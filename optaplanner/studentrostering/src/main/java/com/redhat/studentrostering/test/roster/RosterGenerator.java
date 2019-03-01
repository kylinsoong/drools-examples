package com.redhat.studentrostering.test.roster;

import java.time.LocalTime;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import com.redhat.studentrostering.test.common.generator.StringDataGenerator;

public class RosterGenerator {

	public static class GeneratorType {

        private final String tenantNamePrefix;
        private final StringDataGenerator skillNameGenerator;
        private final StringDataGenerator spotNameGenerator;
        private final List<Pair<LocalTime, LocalTime>> timeslotRangeList; // Start and end time per timeslot
        private final int rotationLength;
        private final int rotationEmployeeListSize;
        private final BiFunction<Integer, Integer, Integer> rotationEmployeeIndexCalculator;

        public GeneratorType(String tenantNamePrefix, StringDataGenerator skillNameGenerator, StringDataGenerator spotNameGenerator,
                             List<Pair<LocalTime, LocalTime>> timeslotRangeList, int rotationLength, int rotationEmployeeListSize,
                             BiFunction<Integer, Integer, Integer> rotationEmployeeIndexCalculator) {
            this.tenantNamePrefix = tenantNamePrefix;
            this.skillNameGenerator = skillNameGenerator;
            this.spotNameGenerator = spotNameGenerator;
            this.timeslotRangeList = timeslotRangeList;
            this.rotationLength = rotationLength;
            this.rotationEmployeeListSize = rotationEmployeeListSize;
            this.rotationEmployeeIndexCalculator = rotationEmployeeIndexCalculator;
        }

		public int getRotationLength() {
			return rotationLength;
		}

		public StringDataGenerator getSkillNameGenerator() {
			return skillNameGenerator;
		}

		public StringDataGenerator getSpotNameGenerator() {
			return spotNameGenerator;
		}

		public List<Pair<LocalTime, LocalTime>> getTimeslotRangeList() {
			return timeslotRangeList;
		}

		public int getRotationEmployeeListSize() {
			return rotationEmployeeListSize;
		}

		public String getTenantNamePrefix() {
			return tenantNamePrefix;
		}

		public BiFunction<Integer, Integer, Integer> getRotationEmployeeIndexCalculator() {
			return rotationEmployeeIndexCalculator;
		}
    }
}
