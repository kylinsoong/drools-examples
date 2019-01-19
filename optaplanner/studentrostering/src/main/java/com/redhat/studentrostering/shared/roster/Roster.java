/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.studentrostering.shared.roster;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

import com.redhat.studentrostering.shared.common.AbstractPersistable;
import com.redhat.studentrostering.shared.employee.Employee;
import com.redhat.studentrostering.shared.employee.EmployeeAvailability;
import com.redhat.studentrostering.shared.shift.Shift;
import com.redhat.studentrostering.shared.skill.Skill;
import com.redhat.studentrostering.shared.spot.Spot;
import com.redhat.studentrostering.shared.tenant.RosterParametrization;

@PlanningSolution
public class Roster extends AbstractPersistable {

	private static final long serialVersionUID = 2635777881241608105L;
	
	@ProblemFactCollectionProperty
    private List<Skill> skillList;
    @ProblemFactCollectionProperty
    private List<Spot> spotList;
    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "employeeRange")
    private List<Employee> employeeList;
    @ProblemFactCollectionProperty
    private List<EmployeeAvailability> employeeAvailabilityList;

    @ProblemFactProperty
    private RosterParametrization rosterParametrization;
    @ProblemFactProperty
    private RosterState rosterState;

    @PlanningEntityCollectionProperty
    private List<Shift> shiftList;

    @PlanningScore
    private HardMediumSoftLongScore score = null;

    @SuppressWarnings("unused")
    public Roster() {}

    public Roster(Long id, Integer tenantId, List<Skill> skillList, List<Spot> spotList, List<Employee> employeeList, List<EmployeeAvailability> employeeAvailabilityList, RosterParametrization rosterParametrization, RosterState rosterState, List<Shift> shiftList) {
        super(id, tenantId);
        this.skillList = skillList;
        this.spotList = spotList;
        this.employeeList = employeeList;
        this.employeeAvailabilityList = employeeAvailabilityList;
        this.rosterParametrization = rosterParametrization;
        this.rosterState = rosterState;
        this.shiftList = shiftList;
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<EmployeeAvailability> getEmployeeAvailabilityList() {
        return employeeAvailabilityList;
    }

    public void setEmployeeAvailabilityList(List<EmployeeAvailability> employeeAvailabilityList) {
        this.employeeAvailabilityList = employeeAvailabilityList;
    }

    public RosterParametrization getRosterParametrization() {
        return rosterParametrization;
    }

    public void setRosterParametrization(RosterParametrization rosterParametrization) {
        this.rosterParametrization = rosterParametrization;
    }

    public RosterState getRosterState() {
        return rosterState;
    }

    public void setRosterState(RosterState rosterState) {
        this.rosterState = rosterState;
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    public HardMediumSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftLongScore score) {
        this.score = score;
    }

}
