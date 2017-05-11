package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

public class Example_KieScanner {

    public static void main(String[] args) {
 
        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId( "com.redhat", "MyProject", "1.0.0-SNAPSHOT" );
        KieContainer kContainer = kieServices.newKieContainer(releaseId);
        KieScanner kScanner = kieServices.newKieScanner( kContainer );
        kScanner.start(1000 * 60 * 30);

    }

}
