package org.kie.examples.phreak.nodes;

public class ProviderRequest {

    private Provider provider;
    
    public ProviderRequest() {
        
    }

    public ProviderRequest(Provider provider) {
        super();
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
