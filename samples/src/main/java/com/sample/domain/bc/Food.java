package com.sample.domain.bc;

import org.kie.api.definition.type.Position;

public class Food {
    
    @Position(0)
    private String item;
    
    @Position(1)
    private String content;

    public Food(String item, String content) {
        super();
        this.item = item;
        this.content = content;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + ((this.content == null) ? 0 : this.content.hashCode());
        result = result * prime + ((this.item == null) ? 0 : this.item.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Food other = (Food)obj;
        if(other.content != null && other.item != null && this.content != null && this.item != null) {
            return other.content.equals(this.content) && other.item.equals(this.item);
        }
        return false;
    }

}
