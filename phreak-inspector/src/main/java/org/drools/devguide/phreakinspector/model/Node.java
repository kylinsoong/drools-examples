/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.phreakinspector.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author esteban
 */
public class Node {
    
    public static enum TYPE {
        ENTRY_POINT("EP", "EP", "#0DFFE7"),
        OBJECT_TYPE("OTN", "OTN","#00D1FF"),
        ALPHA("ALPHA", "A", "#F4F000"),
        BETA("BETA", "B", "#FFA858"),
        FROM("FROM", "F", "#FFFFFF"),
        NOT("NOT", "B", "#E5E4E4"),
        QUERY_ELEMENT("QUERY_ELEMENT", "B", "#FF8FFC"),
        ACCUMULATE("ACCUMULATE", "B", "#FFA4A4"),
        RULE_TERMINAL("RULE_TERMINAL", "TERMINAL", "#FFB4F8"),
        QUERY_TERMINAL("QUERY_TERMINAL", "TERMINAL", "#D1ECFF");
        
        private final String label;
        private final String color;
        private final String group;

        private TYPE(String label, String group, String color) {
            this.label = label;
            this.group = group;
            this.color = color;
        }

        public String getLabel() {
            return label;
        }

        public String getColor() {
            return color;
        }

        public String getGroup() {
            return group;
        }
        
    }
    
    private final Integer id;
    private final String label;
    private final TYPE type;
    private final List<Integer> targetNodesIds = new ArrayList<>();

    public Node(Integer id, String label, TYPE type) {
        this.id = id;
        this.label = label;
        this.type = type;
    }
    
    public void addTargetNode(Integer nodeId){
        this.targetNodesIds.add(nodeId);
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
    
    public String getLabelSafe() {
        return StringEscapeUtils.escapeHtml(this.getLabel());
    }

    public TYPE getType() {
        return type;
    }

    public List<Integer> getTargetNodesIds() {
        return targetNodesIds;
    }

    @Override
    public String toString() {
        return "Node{" + "id=" + id + ", label=" + label + ", type=" + type + ", targetNodesIds=" + targetNodesIds + '}';
    }
    
}
