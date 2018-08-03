package com.sample.queries.model;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;

public class Main {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("ksession-queries");
        
        Cheese c1 = new Cheese( "stinky", 5 );
        Cheese c2 = new Cheese( "stinky", 6 );
        
        Cheese stilton1 = new Cheese( "stilton", 1 );
        Cheese cheddar1 = new Cheese( "cheddar", 1 );
        Cheese stilton2 = new Cheese( "stilton", 2 );
        Cheese cheddar2 = new Cheese( "cheddar", 2 );
        Cheese stilton3 = new Cheese( "stilton", 3 );
        Cheese cheddar3 = new Cheese( "cheddar", 3 );

        ksession.insert( c1 );
        ksession.insert( c2 );
        
        ksession.insert( stilton1 );
        ksession.insert( stilton2 );
        ksession.insert( stilton3 );
        ksession.insert( cheddar1 );
        ksession.insert( cheddar2 );
        ksession.insert( cheddar3 );
        
        QueryResults results = ksession.getQueryResults("simple query");
        
//        System.out.println(results.size());
//        System.out.println(results.getIdentifiers().length);
        
        results.forEach(r ->  {
            System.out.println(r.get("cheese"));
        });
        
        results = ksession.getQueryResults("cheeses");
        Set newSet = new HashSet();
        results.forEach(r ->  {
            List list = new ArrayList();
            list.add( r.get( "stilton" ) );
            list.add( r.get( "cheddar" ) );
            newSet.add( list );
        });
        System.out.println(newSet);
        
        
    }

}
