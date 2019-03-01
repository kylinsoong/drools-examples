package org.optaplanner.examples.nurserostering;

import java.io.File;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.nurserostering.domain.Employee;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;

/**
 * 
 * @author kylin
 *
 */
public class Main {
	
	public static final String SOLVER_CONFIG = "nurserostering/solver/nurseRosteringSolverConfig.xml";

	public static void main(String[] args) {
		
		SolverFactory<NurseRoster> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
		Solver<NurseRoster> solver = solverFactory.buildSolver();
		
		SolutionFileIO<NurseRoster> solutionIO = new XStreamSolutionFileIO<>(NurseRoster.class);
		NurseRoster problem = solutionIO.read(new File("data/nurserostering/unsolved/medium01.xml"));
		
		for (Employee employee : problem.getEmployeeList()) {
			System.out.println(employee + ": " + employee.getDayOnRequestMap());
		}
		
		NurseRoster nurseRoster = solver.solve(problem);
		
		for (Employee employee : nurseRoster.getEmployeeList()) {
			System.out.println(employee + ": " + employee.getDayOnRequestMap());
		}
		
//		solutionIO.write(nurseRoster, new File("data/nurserostering/solved/medium01.xml"));

		System.out.println(nurseRoster);
	}

}
