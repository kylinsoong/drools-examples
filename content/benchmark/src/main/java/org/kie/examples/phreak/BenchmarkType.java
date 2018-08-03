package org.kie.examples.phreak;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.examples.phreak.util.DataGenerator;
import org.kie.internal.builder.conf.RuleEngineOption;

public enum BenchmarkType {

    GROUPING("groupingKBase") {
        @Override
        public void execute(final DataGenerator data, final KieSession ksession) {
            data.insertInto(ksession);
            ksession.getAgenda().getAgendaGroup("gold").setFocus();
            ksession.fireAllRules();
        }
    },
    MODIFICATION("modificationKBase"), LAZINESS3("laziness3KBase"), LAZINESS6("laziness6KBase");

    private final KieBase PHREAK_BASE;
    private final KieBase RETEOO_BASE;

    BenchmarkType(final String kbaseName) {
        final KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
        // phreak
        KieBaseConfiguration kconfig = KieServices.Factory.get().newKieBaseConfiguration();
        kconfig.setOption(RuleEngineOption.PHREAK);
        this.PHREAK_BASE = container.newKieBase(kbaseName, kconfig);
        // rete
        kconfig = KieServices.Factory.get().newKieBaseConfiguration();
        kconfig.setOption(RuleEngineOption.RETEOO);
        this.RETEOO_BASE = container.newKieBase(kbaseName, kconfig);
    }

    public void execute(final DataGenerator data, final KieSession ksession) {
        data.insertInto(ksession);
        ksession.fireAllRules();
    }

    public KieSession getPhreakKieSession() {
        return this.PHREAK_BASE.newKieSession();
    }

    public KieSession getReteOOKieSession() {
        return this.RETEOO_BASE.newKieSession();
    }

}
