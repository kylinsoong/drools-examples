package com.redhat.studentrostering.test;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.termination.TerminationConfig;

import com.redhat.studentrostering.shared.employee.Employee;
import com.redhat.studentrostering.shared.employee.EmployeeAvailability;
import com.redhat.studentrostering.shared.employee.EmployeeAvailabilityState;
import com.redhat.studentrostering.shared.roster.Roster;
import com.redhat.studentrostering.shared.roster.RosterState;
import com.redhat.studentrostering.shared.rotation.ShiftTemplate;
import com.redhat.studentrostering.shared.shift.Shift;
import com.redhat.studentrostering.shared.skill.Skill;
import com.redhat.studentrostering.shared.spot.Spot;
import com.redhat.studentrostering.shared.tenant.RosterParametrization;
import com.redhat.studentrostering.shared.tenant.Tenant;
import com.redhat.studentrostering.test.admin.SystemPropertiesRetriever;
import com.redhat.studentrostering.test.common.generator.StringDataGenerator;
import com.redhat.studentrostering.test.roster.RosterGenerator.GeneratorType;

/**
 * 
 * @author kylin
 *
 */
public class Main {
	
	public static final String SOLVER_CONFIG = "com/redhat/studentrostering/server/solver/employeeRosteringSolverConfig.xml";
	
    private static final double[] EXTRA_SHIFT_THRESHOLDS = {0.5, 0.8, 0.95};
	
	static Random random = new Random(37);
	
	static AtomicInteger tenantIdGenerator = new AtomicInteger(0);
	
	static StringDataGenerator employeeNameGenerator = StringDataGenerator.buildFullNames();
	
	static final GeneratorType hospitalGeneratorType = new GeneratorType(
            "Hospital",
            new StringDataGenerator()
                    .addPart(
                            "Ambulatory care",
                            "Critical care",
                            "Midwife",
                            "Gastroenterology",
                            "Neuroscience",
                            "Oncology",
                            "Pediatric",
                            "Psychiatric",
                            "Geriatric",
                            "Radiology")
                    .addPart(
                            "nurse",
                            "physician",
                            "doctor",
                            "attendant",
                            "specialist",
                            "surgeon",
                            "medic",
                            "practitioner",
                            "pharmacist",
                            "researcher"),
            new StringDataGenerator(true)
                    .addPart(false, 0,
                             "Basic",
                             "Advanced",
                             "Expert",
                             "Specialized",
                             "Elder",
                             "Child",
                             "Infant",
                             "Baby",
                             "Male",
                             "Female",
                             "Common",
                             "Uncommon",
                             "Research",
                             "Administrative",
                             "Regressing")
                    .addPart(true, 1,
                             "anaesthetics",
                             "cardiology",
                             "critical care",
                             "emergency",
                             "ear nose throat",
                             "gastroenterology",
                             "haematology",
                             "maternity",
                             "neurology",
                             "oncology",
                             "ophthalmology",
                             "orthopaedics",
                             "physiotherapy",
                             "radiotherapy",
                             "urology")
                    .addPart(false, 0,
                             "Alpha",
                             "Beta",
                             "Gamma",
                             "Delta",
                             "Epsilon",
                             "Zeta",
                             "Eta",
                             "Theta",
                             "Iota",
                             "Kappa",
                             "Lambda",
                             "Mu",
                             "Nu",
                             "Xi",
                             "Omicron"),
            Arrays.asList(
                    Pair.of(LocalTime.of(6, 0), LocalTime.of(14, 0)),
                    Pair.of(LocalTime.of(9, 0), LocalTime.of(17, 0)),
                    Pair.of(LocalTime.of(14, 0), LocalTime.of(22, 0)),
                    Pair.of(LocalTime.of(22, 0), LocalTime.of(6, 0))),
            // Morning:   A A A A A D D B B B B B D D C C C C C D D
            // Day:       F F B B B F F F F C C C F F F F A A A F F
            // Afternoon: D D D E E E E D D D E E E E D D D E E E E
            // Night:     E C C C C C C E A A A A A A E B B B B B B
            21, 6, (startDayOffset, timeslotRangesIndex) -> {
        switch (timeslotRangesIndex) {
            case 0:
                return startDayOffset % 7 >= 5 ? 3 : startDayOffset / 7;
            case 1:
                return (startDayOffset + 2) % 7 < 4 ? 5 : (startDayOffset - 16 + 21) % 21 / 7;
            case 2:
                return startDayOffset % 7 < 3 ? 3 : 4;
            case 3:
                return startDayOffset % 7 < 1 ? 4 : (startDayOffset - 8 + 21) % 21 / 7;
            default:
                throw new IllegalStateException("Impossible state for timeslotRangesIndex (" + timeslotRangesIndex + ").");
        }
    });

	public static void main(String[] args) {

		Roster roster = generateRoster();
		
		System.out.println("Total shifts need to plan: " + roster.getShiftList().size());
		System.out.println(roster.getShiftList());
		
		SolverFactory<Roster> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        solverFactory.getSolverConfig().setTerminationConfig(new TerminationConfig().withMillisecondsSpentLimit(2000L));
        Solver<Roster> solver = solverFactory.buildSolver();
        
        Roster results = solver.solve(roster);
        
        System.out.println("After planning: " + results.getShiftList().size());
        results.getShiftList().forEach(s -> {
        	
        	if(s.getEmployee() != null) {
        		System.out.println("[" + s.getSpot() + "], [" + s.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " - " + s.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "], [" + s.getEmployee() + "]");
        	}
        });
	}
	
	static Roster generateRoster() {
		ZoneId zoneId = SystemPropertiesRetriever.determineZoneId();
		Integer tenantId = tenantIdGenerator.getAndIncrement();
		Integer skillListSize = 2;
		Integer spotListSize = 3;
		Integer employeeListSize = 5;
		
		Tenant tenant = new Tenant("Hospital Pumch (10 students)");
		tenant.setId(tenantId);
		
		RosterParametrization rosterParametrization = new RosterParametrization();
		rosterParametrization.setId(generateID());
        rosterParametrization.setTenantId(tenantId);
        
        RosterState rosterState = new RosterState();
        rosterState.setTenantId(tenantId);
        int publishNotice = 14;
        rosterState.setPublishNotice(publishNotice);
        LocalDate firstDraftDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).plusDays(publishNotice);
        rosterState.setFirstDraftDate(firstDraftDate);
        rosterState.setDraftLength(14);
        rosterState.setDraftLength(14);
        rosterState.setUnplannedRotationOffset(0);
        rosterState.setRotationLength(hospitalGeneratorType.getRotationLength());
        rosterState.setLastHistoricDate(LocalDate.now().minusDays(1));
        rosterState.setTimeZone(zoneId);
        rosterState.setTenant(tenant);
        rosterState.setId(generateID());
        
        List<Skill> skillList = createSkillList(hospitalGeneratorType, tenantId, skillListSize);
        List<Spot> spotList = createSpotList(hospitalGeneratorType, tenantId, spotListSize, skillList);
        List<Employee> employeeList = createEmployeeList(hospitalGeneratorType, tenantId, employeeListSize, skillList);
        List<ShiftTemplate> shiftTemplateList = createShiftTemplateList(hospitalGeneratorType, tenantId, rosterState, spotList, employeeList);
        List<Shift> shiftList = createShiftList(hospitalGeneratorType, tenantId, rosterParametrization, rosterState, spotList, shiftTemplateList);
        List<EmployeeAvailability> employeeAvailabilityList = createEmployeeAvailabilityList(hospitalGeneratorType, tenantId, rosterParametrization, rosterState, employeeList, shiftList);
        
        Roster roster = new Roster((long) tenantId, tenantId, skillList, spotList, employeeList, employeeAvailabilityList, rosterParametrization, rosterState, shiftList);
        roster.setId(generateID());
        
        return roster;
	}

    private static Long generateID() {
		return new Long(tenantIdGenerator.getAndIncrement());
	}

	private static List<EmployeeAvailability> createEmployeeAvailabilityList(GeneratorType generatorType, Integer tenantId, RosterParametrization rosterParametrization, RosterState rosterState, List<Employee> employeeList, List<Shift> shiftList) {
    	ZoneId zoneId = rosterState.getTimeZone();
        // Generate a feasible published schedule: no EmployeeAvailability instancer during the published period
        // nor on the first draft day (because they might overlap with shift on the last published day)
        LocalDate date = rosterState.getFirstDraftDate().plusDays(1);
        LocalDate firstUnplannedDate = rosterState.getFirstUnplannedDate();
        List<EmployeeAvailability> employeeAvailabilityList = new ArrayList<>();
        Map<LocalDate, List<Shift>> startDayToShiftListMap = shiftList.stream()
                .collect(groupingBy(shift -> shift.getStartDateTime().toLocalDate()));

        while (date.compareTo(firstUnplannedDate) < 0) {
            List<Shift> dayShiftList = startDayToShiftListMap.get(date);
            List<Employee> availableEmployeeList = new ArrayList<>(employeeList);
            int stateCount = (employeeList.size() - dayShiftList.size()) / 4;
            if (stateCount <= 0) {
                // Heavy overconstrained planning (more shifts than employees)
                stateCount = 1;
            }
            for (EmployeeAvailabilityState state : EmployeeAvailabilityState.values()) {
                for (int i = 0; i < stateCount; i++) {
                    Employee employee = availableEmployeeList.remove(random.nextInt(availableEmployeeList.size()));
                    LocalDateTime startDateTime = date.atTime(LocalTime.MIN);
                    LocalDateTime endDateTime = date.plusDays(1).atTime(LocalTime.MIN);
                    OffsetDateTime startOffsetDateTime = OffsetDateTime.of(startDateTime, zoneId.getRules().getOffset(startDateTime));
                    OffsetDateTime endOffsetDateTime = OffsetDateTime.of(endDateTime, zoneId.getRules().getOffset(endDateTime));
                    EmployeeAvailability employeeAvailability = new EmployeeAvailability(tenantId, employee, startOffsetDateTime, endOffsetDateTime);
                    employeeAvailability.setState(state);
                    employeeAvailability.setId(generateID());
                    employeeAvailabilityList.add(employeeAvailability);
                }
            }
            date = date.plusDays(1);
        }
        return employeeAvailabilityList;
	}

	private static List<Shift> createShiftList(GeneratorType generatorType, Integer tenantId, RosterParametrization rosterParametrization, RosterState rosterState, List<Spot> spotList, List<ShiftTemplate> shiftTemplateList) {
    	
		ZoneId zoneId = rosterState.getTimeZone();
        int rotationLength = rosterState.getRotationLength();
        LocalDate date = rosterState.getLastHistoricDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate firstDraftDate = rosterState.getFirstDraftDate();
        LocalDate firstUnplannedDate = rosterState.getFirstUnplannedDate();

        List<Shift> shiftList = new ArrayList<>();
        Map<Pair<Integer, Spot>, List<ShiftTemplate>> dayOffsetAndSpotToShiftTemplateListMap = shiftTemplateList.stream()
                .collect(groupingBy(shiftTemplate -> Pair.of(shiftTemplate.getStartDayOffset(), shiftTemplate.getSpot())));
        int dayOffset = 0;
        while (date.compareTo(firstUnplannedDate) < 0) {
            for (Spot spot : spotList) {
                List<ShiftTemplate> subShiftTemplateList = dayOffsetAndSpotToShiftTemplateListMap.get(Pair.of(dayOffset, spot));
                for (ShiftTemplate shiftTemplate : subShiftTemplateList) {
                    boolean defaultToRotationEmployee = date.compareTo(firstDraftDate) < 0;
                    Shift shift = shiftTemplate.createShiftOnDate(date, rosterState.getRotationLength(),zoneId, defaultToRotationEmployee);
                    shift.setId(generateID());
                    shiftList.add(shift);
                }
                if (date.compareTo(firstDraftDate) >= 0) {
                    int extraShiftCount = generateRandomIntFromThresholds(EXTRA_SHIFT_THRESHOLDS);
                    for (int i = 0; i < extraShiftCount; i++) {
                        ShiftTemplate shiftTemplate = extractRandomElement(subShiftTemplateList);
                        Shift shift = shiftTemplate.createShiftOnDate(date, rosterState.getRotationLength(), zoneId, false);
                        shift.setId(generateID());
                        shiftList.add(shift);
                    }
                }
            }
            date = date.plusDays(1);
            dayOffset = (dayOffset + 1) % rotationLength;
        }
        rosterState.setUnplannedRotationOffset(dayOffset);
        return shiftList;
	}
    
    static <E> E extractRandomElement(List<E> list) {
        return list.get(random.nextInt(list.size()));
    }

	private static List<ShiftTemplate> createShiftTemplateList(GeneratorType generatorType, Integer tenantId, RosterState rosterState, List<Spot> spotList, List<Employee> employeeList) {
    	int rotationLength = rosterState.getRotationLength();
        List<ShiftTemplate> shiftTemplateList = new ArrayList<>(spotList.size() * rotationLength * generatorType.getTimeslotRangeList().size());
        List<Employee> remainingEmployeeList = new ArrayList<>(employeeList);
        for (Spot spot : spotList) {
            List<Employee> rotationEmployeeList = remainingEmployeeList.stream()
                    .filter(employee -> employee.getSkillProficiencySet().containsAll(spot.getRequiredSkillSet()))
                    .limit(generatorType.getRotationEmployeeListSize()).collect(toList());
            remainingEmployeeList.removeAll(rotationEmployeeList);
            // For every day in the rotation (independent of publishLength and draftLength)
            for (int startDayOffset = 0; startDayOffset < rotationLength; startDayOffset++) {
                // Fill the offset day with shift templates
                for (int timeslotRangesIndex = 0; timeslotRangesIndex < generatorType.getTimeslotRangeList().size(); timeslotRangesIndex++) {
                    Pair<LocalTime, LocalTime> timeslotRange = generatorType.getTimeslotRangeList().get(timeslotRangesIndex);
                    LocalTime startTime = timeslotRange.getLeft();
                    LocalTime endTime = timeslotRange.getRight();
                    int endDayOffset = startDayOffset;
                    if (endTime.compareTo(startTime) < 0) {
                        endDayOffset = (startDayOffset + 1) % rotationLength;
                    }
                    int rotationEmployeeIndex = generatorType.getRotationEmployeeIndexCalculator().apply(startDayOffset, timeslotRangesIndex);
                    if (rotationEmployeeIndex < 0 || rotationEmployeeIndex >= generatorType.getRotationEmployeeListSize()) {
                        throw new IllegalStateException(
                                "The rotationEmployeeIndexCalculator for generatorType (" + generatorType.getTenantNamePrefix() + ") returns an invalid rotationEmployeeIndex (" +
                                        rotationEmployeeIndex + ") for startDayOffset (" + startDayOffset + ") and timeslotRangesIndex (" + timeslotRangesIndex + ").");
                    }
                    // There might be less employees than we need (overconstrained planning)
                    Employee rotationEmployee = rotationEmployeeIndex >= rotationEmployeeList.size() ? null : rotationEmployeeList.get(rotationEmployeeIndex);
                    ShiftTemplate shiftTemplate = new ShiftTemplate(tenantId, spot, startDayOffset, startTime, endDayOffset, endTime, rotationEmployee);
                    shiftTemplate.setId(generateID());
                    shiftTemplateList.add(shiftTemplate);
                }
            }
        }
        return shiftTemplateList;
	}

	private static List<Employee> createEmployeeList(GeneratorType generatorType, Integer tenantId, Integer size, List<Skill> generalSkillList) {
    	List<Employee> employeeList = new ArrayList<>(size);
        employeeNameGenerator.predictMaximumSizeAndReset(size);
        for (int i = 0; i < size; i++) {
            String name = employeeNameGenerator.generateNextValue();
            HashSet<Skill> skillProficiencySet = new HashSet<>(extractRandomSubList(generalSkillList, 0.1, 0.3, 0.5, 0.7, 0.9, 1.0));
            Employee employee = new Employee(tenantId, name);
            employee.setSkillProficiencySet(skillProficiencySet);
            employee.setId(generateID());
            employeeList.add(employee);
        }
        return employeeList;
	}

	private static List<Spot> createSpotList(GeneratorType generatorType, Integer tenantId, Integer size, List<Skill> skillList) {
		
    	List<Spot> spotList = new ArrayList<>(size);
    	generatorType.getSpotNameGenerator().predictMaximumSizeAndReset(size);
    	for (int i = 0; i < size; i++) {
            String name = generatorType.getSpotNameGenerator().generateNextValue();
            Set<Skill> requiredSkillSet = new HashSet<>(extractRandomSubList(skillList, 0.5, 0.9, 1.0));
            Spot spot = new Spot(tenantId, name, requiredSkillSet);
            spot.setId(generateID());
            spotList.add(spot);
        }
        return spotList;
	}
    
    private static <E> List<E> extractRandomSubList(List<E> list, double... thresholds) {
        int size = generateRandomIntFromThresholds(thresholds);
        if (size > list.size()) {
            size = list.size();
        }
        return extractRandomSubListOfSize(list, size);
    }
    
    private static int generateRandomIntFromThresholds(double... thresholds) {
        double randomDouble = random.nextDouble();
        for (int i = 0; i < thresholds.length; i++) {
            if (randomDouble < thresholds[i]) {
                return i;
            }
        }
        return thresholds.length;
    }

    private static <E> List<E> extractRandomSubListOfSize(List<E> list, int size) {
        List<E> subList = new ArrayList<>(list);
        Collections.shuffle(subList, random);
        // Remove elements not in the sublist (so it can be garbage collected)
        subList.subList(size, subList.size()).clear();
        return subList;
    }

	private static List<Skill> createSkillList(GeneratorType generatorType, Integer tenantId, Integer size) {
		
    	List<Skill> skillList = new ArrayList<>(size);
    	generatorType.getSkillNameGenerator().predictMaximumSizeAndReset(2);
    	for (int i = 0; i < size; i++) {
            String name = generatorType.getSkillNameGenerator().generateNextValue();
            Skill skill = new Skill(tenantId, name);
            skill.setId(generateID());
            skillList.add(skill);            
        }
        return skillList;    	
	}

}
