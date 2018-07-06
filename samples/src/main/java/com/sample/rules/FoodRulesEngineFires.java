package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.bc.Food;
import com.sample.domain.bc.Question;
import com.sample.domain.bc.QuestionType;
import com.sample.domain.bc.Sandwich;

public class FoodRulesEngineFires {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession session = container.newKieSession("ksession-bc");
        
        session.insert(new Food("mayonnaise", "egg"));
        session.insert(new Food("mayonnaise", "oil"));
        session.insert(new Food("mayonnaise", "vinegar"));
        session.insert(new Food("mustard", "vinegar"));
        session.insert(new Food("mustard", "turmeric"));
        session.insert(new Food("mustard", "mustard seed"));
        session.insert(new Food("tuna salad", "albacore tuna"));
        session.insert(new Food("tuna salad", "mayonnaise"));
        session.insert(new Food("tuna salad", "relish"));
        session.insert(new Food("relish", "cucumber"));
        session.insert(new Food("relish", "vinegar"));
        session.insert(new Food("relish", "sugar"));
        session.insert(new Food("peanut butter", "sugar"));
        session.insert(new Food("peanut butter", "roasted peanuts"));
        session.insert(new Food("peanut butter", "oil"));
        session.insert(new Food("jelly", "sugar"));
        session.insert(new Food("jelly", "grapes"));
        session.insert(new Food("jelly", "pectin"));
        session.insert(new Food("Egg Salad Sandwich", "boiled egg"));
        session.insert(new Food("Egg Salad Sandwich", "mayonnaise"));
        session.insert(new Food("Egg Salad Sandwich", "white bread"));
        session.insert(new Food("Ham Sandwich", "ham"));
        session.insert(new Food("Ham Sandwich", "wheat bread"));
        session.insert(new Food("Ham Sandwich", "mustard"));
        session.insert(new Sandwich("Ham Sandwich"));
        session.insert(new Food("Ham and Cheese Sandwich", "ham"));
        session.insert(new Food("Ham and Cheese Sandwich", "wheat bread"));
        session.insert(new Food("Ham and Cheese Sandwich", "mustard"));
        session.insert(new Food("Ham and Cheese Sandwich", "swiss cheese"));
        session.insert(new Sandwich("Ham and Cheese Sandwich"));
        session.insert(new Food("Grilled Cheese Sandwich", "american cheese"));
        session.insert(new Food("Grilled Cheese Sandwich", "white bread"));
        session.insert(new Food("Grilled Cheese Sandwich", "butter"));
        session.insert(new Sandwich("Grilled Cheese Sandwich"));
        session.insert(new Sandwich("Egg Salad Sandwich"));
        session.insert(new Food("Tuna Sandwich", "wheat bread"));
        session.insert(new Food("Tuna Sandwich","tuna salad"));
        session.insert(new Sandwich("Tuna Sandwich"));
        session.insert(new Food("PBJ Sandwich", "peanut butter"));
        session.insert(new Food("PBJ Sandwich","jelly"));
        session.insert(new Food("PBJ Sandwich","white bread"));
        session.insert(new Sandwich("PBJ Sandwich"));
        
//        session.insert(new Question(QuestionType.MENU));
//        session.insert(new Question(QuestionType.CONTAINS_WHAT,"Tuna Sandwich"));
//        session.insert(new Question(QuestionType.WHAT_CONTAINS,"vinegar"));
        session.insert(new Question(QuestionType.COMPACT,"Tuna Sandwich"));
                
        session.fireAllRules();    
                
        session.dispose();
        
    }

}
