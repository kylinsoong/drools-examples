/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.phreakinspector.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.drools.core.base.ClassObjectType;
import org.drools.core.base.dataproviders.MVELDataProvider;
import org.drools.core.common.EmptyBetaConstraints;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.drools.core.reteoo.AccumulateNode;
import org.drools.core.reteoo.AlphaNode;
import org.drools.core.reteoo.EntryPointNode;
import org.drools.core.reteoo.FromNode;
import org.drools.core.reteoo.JoinNode;
import org.drools.core.reteoo.LeftInputAdapterNode;
import org.drools.core.reteoo.LeftTupleSink;
import org.drools.core.reteoo.LeftTupleSinkPropagator;
import org.drools.core.reteoo.NotNode;
import org.drools.core.reteoo.ObjectSink;
import org.drools.core.reteoo.ObjectTypeNode;
import org.drools.core.reteoo.QueryElementNode;
import org.drools.core.reteoo.QueryTerminalNode;
import org.drools.core.reteoo.Rete;
import org.drools.core.reteoo.RightInputAdapterNode;
import org.drools.core.reteoo.RuleTerminalNode;
import org.drools.core.reteoo.Sink;
import org.drools.core.rule.Declaration;
import org.drools.core.rule.EntryPointId;
import org.drools.core.rule.constraint.MvelConstraint;
import org.drools.core.spi.BetaNodeFieldConstraint;
import org.drools.core.spi.ObjectType;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.utils.KieHelper;
import org.stringtemplate.v4.ST;

/**
 *
 * @author esteban
 */
public class PhreakInspector {

    private final Map<Integer, Node> nodes = new HashMap<>();

    public InputStream fromClassPathKieContainer(String kieBaseName) throws IOException {
        return this.fromKieBase(this.createContainer().getKieBase(kieBaseName));
    }

    public InputStream fromResources(Map<Resource, ResourceType> resources) throws IOException {
        return this.fromKieBase(this.buildKieBase(resources));
    }

    public InputStream fromKieBase(KieBase kb) throws IOException {

        KnowledgeBaseImpl kbase = (KnowledgeBaseImpl) kb;

        Rete rete = kbase.getRete();

        Map<EntryPointId, EntryPointNode> entryPointNodes = rete.getEntryPointNodes();
        for (EntryPointNode value : entryPointNodes.values()) {

            Node epNode = new Node(value.getId(), value.getEntryPoint().getEntryPointId(), Node.TYPE.ENTRY_POINT);
            nodes.put(epNode.getId(), epNode);

            Map<ObjectType, ObjectTypeNode> objectTypeNodes = value.getObjectTypeNodes();
            for (ObjectTypeNode otn : objectTypeNodes.values()) {

                String nodeLabel = "";
                if (otn.getObjectType() instanceof ClassObjectType) {
                    nodeLabel = ((ClassObjectType) otn.getObjectType()).getClassName();
                } else {
                    nodeLabel = otn.getObjectType().toString();
                }

                Node otNode = new Node(otn.getId(), nodeLabel, Node.TYPE.OBJECT_TYPE);
                nodes.put(otNode.getId(), otNode);
                epNode.addTargetNode(otNode.getId());

                ObjectSink[] sinks = otn.getObjectSinkPropagator().getSinks();
                for (ObjectSink sink : sinks) {
                    this.visitObjectSink(sink, otNode);
                }

            }
        }

//        //Segments
//        InternalWorkingMemory wm = ((InternalWorkingMemory)kbase.newStatefulKnowledgeSession());
//        for (EntryPointNode value : entryPointNodes.values()) {
//            Map<ObjectType, ObjectTypeNode> objectTypeNodes = value.getObjectTypeNodes();
//            for (ObjectTypeNode otn : objectTypeNodes.values()) {
//                if (otn.getSinkPropagator().getSinks().length == 0){
//                    continue;
//                }
//                ObjectSink tmp = otn.getSinkPropagator().getSinks()[0];
//                wm.getNodeMemory(tmp);
//                
//                LeftInputAdapterNode liaNode = (LeftInputAdapterNode) otn.getSinkPropagator().getSinks()[0];
//
//                LeftInputAdapterNode.LiaNodeMemory liaMem = ( LeftInputAdapterNode.LiaNodeMemory ) wm.getNodeMemory( liaNode ); 
//
//                System.out.println("Found a memory");
//            }
//        }
        return this.generateGraphViz(nodes);
    }

    private void visitObjectSink(ObjectSink oSink, Node parentNode) {
        this.visitSink(oSink, parentNode);
    }

    private void visitLeftTupleSink(LeftTupleSink ltSink, Node parentNode) {
        this.visitSink(ltSink, parentNode);
    }

    private void visitSink(Sink sink, Node parentNode) {
        if (sink instanceof LeftInputAdapterNode) {
            LeftInputAdapterNode lian = (LeftInputAdapterNode) sink;
            this.visitLeftInputAdapterNode(lian, parentNode);
        } else if (sink instanceof RightInputAdapterNode) {
            RightInputAdapterNode rian = (RightInputAdapterNode) sink;
            this.visitRightInputAdapterNode(rian, parentNode);
        } else if (sink instanceof AlphaNode) {
            AlphaNode alpha = (AlphaNode) sink;
            this.visitAlphaNode(alpha, parentNode);
        } else if (sink instanceof JoinNode) {
            JoinNode join = (JoinNode) sink;
            this.visitBetaNode(join, parentNode);
        } else if (sink instanceof NotNode) {
            NotNode not = (NotNode) sink;
            this.visitNotNode(not, parentNode);
        } else if (sink instanceof QueryElementNode) {
            QueryElementNode qen = (QueryElementNode) sink;
            this.visitQueryElementNode(qen, parentNode);
        } else if (sink instanceof AccumulateNode) {
            AccumulateNode acc = (AccumulateNode) sink;
            this.visitAccumulateNode(acc, parentNode);
        } else if (sink instanceof RuleTerminalNode) {
            RuleTerminalNode rt = (RuleTerminalNode) sink;
            this.visitRuleTerminalNode(rt, parentNode);
        } else if (sink instanceof QueryTerminalNode) {
            QueryTerminalNode qt = (QueryTerminalNode) sink;
            this.visitQueryTerminalNode(qt, parentNode);
        } else if (sink instanceof FromNode) {
            FromNode from = (FromNode) sink;
            this.visitFromNode(from, parentNode);
        } else {
            throw new UnsupportedOperationException(sink.toString());
        }
    }

    private void visitLeftInputAdapterNode(LeftInputAdapterNode lian, Node parentNode) {
        LeftTupleSink[] ltSinks = lian.getSinkPropagator().getSinks();
        for (LeftTupleSink ltSink : ltSinks) {
            visitLeftTupleSink(ltSink, parentNode);
        }
    }

    private void visitRightInputAdapterNode(RightInputAdapterNode rian, Node parentNode) {
        ObjectSink[] oSinks = rian.getObjectSinkPropagator().getSinks();
        for (ObjectSink oSink : oSinks) {
            this.visitObjectSink(oSink, parentNode);
        }
    }

    private void visitAlphaNode(AlphaNode alpha, Node parentNode) {
        Node alphaNode = new Node(alpha.getId(), alpha.getConstraint().toString(), Node.TYPE.ALPHA);
        nodes.put(alphaNode.getId(), alphaNode);
        parentNode.addTargetNode(alphaNode.getId());

        ObjectSink[] oSinks = alpha.getObjectSinkPropagator().getSinks();
        for (ObjectSink oSink2 : oSinks) {
            this.visitObjectSink(oSink2, alphaNode);
        }
    }

    private void visitBetaNode(JoinNode join, Node parentNode) {
        Node betaNode = new Node(join.getId(), this.createConstraintsString(join), Node.TYPE.BETA);
        nodes.put(betaNode.getId(), betaNode);
        parentNode.addTargetNode(betaNode.getId());

        LeftTupleSinkPropagator ltsp = join.getSinkPropagator();
        LeftTupleSink[] sinks = ltsp.getSinks();
        for (LeftTupleSink ltSink : sinks) {
            visitLeftTupleSink(ltSink, betaNode);
        }
    }

    private void visitFromNode(FromNode from, Node parentNode) {
        Node fromNode = new Node(from.getId(), this.createConstraintsString(from), Node.TYPE.FROM);
        nodes.put(fromNode.getId(), fromNode);
        parentNode.addTargetNode(fromNode.getId());

        LeftTupleSinkPropagator ltsp = from.getSinkPropagator();
        LeftTupleSink[] sinks = ltsp.getSinks();
        for (LeftTupleSink ltSink : sinks) {
            visitLeftTupleSink(ltSink, fromNode);
        }
    }

    private void visitNotNode(NotNode not, Node parentNode) {
        Node notNode = new Node(not.getId(), not.toString(), Node.TYPE.NOT);
        nodes.put(notNode.getId(), notNode);
        parentNode.addTargetNode(notNode.getId());

        LeftTupleSinkPropagator ltsp = not.getSinkPropagator();
        LeftTupleSink[] sinks = ltsp.getSinks();
        for (LeftTupleSink ltSink : sinks) {
            visitLeftTupleSink(ltSink, notNode);
        }
    }

    private void visitQueryElementNode(QueryElementNode qen, Node parentNode) {
        Node queryNode = new Node(qen.getId(), qen.toString(), Node.TYPE.QUERY_ELEMENT);
        nodes.put(queryNode.getId(), queryNode);
        parentNode.addTargetNode(queryNode.getId());

        LeftTupleSinkPropagator ltsp = qen.getSinkPropagator();
        LeftTupleSink[] sinks = ltsp.getSinks();
        for (LeftTupleSink ltSink : sinks) {
            visitLeftTupleSink(ltSink, queryNode);
        }
    }

    private void visitAccumulateNode(AccumulateNode an, Node parentNode) {
        Node accNode = new Node(an.getId(), an.toString(), Node.TYPE.ACCUMULATE);
        nodes.put(accNode.getId(), accNode);
        parentNode.addTargetNode(accNode.getId());

        LeftTupleSinkPropagator ltsp = an.getSinkPropagator();
        LeftTupleSink[] sinks = ltsp.getSinks();
        for (LeftTupleSink ltSink : sinks) {
            visitLeftTupleSink(ltSink, accNode);
        }
    }

    private void visitRuleTerminalNode(RuleTerminalNode rtn, Node parentNode) {
        Node rtNode = new Node(rtn.getId(), rtn.getRule().getName(), Node.TYPE.RULE_TERMINAL);
        nodes.put(rtNode.getId(), rtNode);
        parentNode.addTargetNode(rtNode.getId());
    }

    private void visitQueryTerminalNode(QueryTerminalNode qtn, Node parentNode) {
        Node qtNode = new Node(qtn.getId(), qtn.getRule().getName(), Node.TYPE.QUERY_TERMINAL);
        nodes.put(qtNode.getId(), qtNode);
        parentNode.addTargetNode(qtNode.getId());
    }

    private String createConstraintsString(JoinNode joinNode) {
        String result = "";
        BetaNodeFieldConstraint[] constraints = joinNode.getConstraints();
        if (constraints == null) {
            return result;
        }

        for (BetaNodeFieldConstraint constraint : constraints) {
            if (constraint instanceof EmptyBetaConstraints) {
                //do nothing
            } else if (constraint instanceof MvelConstraint) {
                result = ((MvelConstraint) constraint).getExpression() + ",";
            }
        }

        return result;
    }

    private String createConstraintsString(FromNode fromNode) {
        String result = "<from expression here>";
        MVELDataProvider provider = (MVELDataProvider) fromNode.getDataProvider();
        if (provider == null) {
            return result;
        }
        
       

        return result;
    }

    private InputStream generateGraphViz(Map<Integer, Node> nodes) throws IOException {

        String template = IOUtils.toString(PhreakInspector.class
                .getResourceAsStream("/templates/viz.template"));
        ST st = new ST(template, '$', '$');
        st.add("items", nodes.values());

        Map<String, List<Node>> itemsByGroup = nodes.values().stream().collect(Collectors.groupingBy(n -> n.getType().getGroup()));
        st.add("itemsByGroup", itemsByGroup);

        return new ByteArrayInputStream(st.render().getBytes());

    }

    private KieContainer createContainer() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        this.assertBuildResults(kContainer.verify());

        return kContainer;
    }

    private KieBase buildKieBase(Map<Resource, ResourceType> resources) {
        KieHelper kieHelper = new KieHelper();

        for (Map.Entry<Resource, ResourceType> entrySet : resources.entrySet()) {
            kieHelper.addResource(entrySet.getKey(), entrySet.getValue());
        }

        this.assertBuildResults(kieHelper.verify());

        return kieHelper.build();
    }

    private void assertBuildResults(Results results) {
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.printf("[%s] - %s[%s,%s]: %s", message.getLevel(), message.getPath(), message.getLine(), message.getColumn(), message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
    }
}
