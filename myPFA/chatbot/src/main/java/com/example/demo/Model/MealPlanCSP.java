//package com.example.demo.Model;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import org.hibernate.mapping.Constraint;
//
//public class MealPlanCSP {
//
//    private final Map<String, List<Meal>> domains;
//    private final List<Constraint> constraints;
//
//    public MealPlanCSP() {
//        this.domains = new HashMap<>();
//        this.constraints = new ArrayList<>();
//    }
//
//    public void addVariable(String variableName, List<Meal> domain) {
//        domains.put(variableName, domain);
//    }
//
//    public void addConstraint(Constraint constraint) {
//        constraints.add(constraint);
//    }
//
//    public Map<String, Meal> solve() {
//        Map<String, Meal> assignment = new HashMap<>();
//        return backtrack(assignment) ? assignment : null;
//    }
//
//    private boolean backtrack(Map<String, Meal> assignment) {
//        if (isAssignmentComplete(assignment)) {
//            return true;
//        }
//
//        String variable = selectUnassignedVariable(assignment);
//        List<Meal> domain = domains.get(variable);
//
//        for (Meal value : domain) {
//            if (isValueConsistent(variable, value, assignment)) {
//                assignment.put(variable, value);
//
//                if (backtrack(assignment)) {
//                    return true;
//                }
//
//                assignment.remove(variable);
//            }
//        }
//
//        return false;
//    }
//
//    private boolean isAssignmentComplete(Map<String, Meal> assignment) {
//        return assignment.keySet().equals(domains.keySet());
//    }
//
//    private String selectUnassignedVariable(Map<String, Meal> assignment) {
//        for (String variable : domains.keySet()) {
//            if (!assignment.containsKey(variable)) {
//                return variable;
//            }
//        }
//        return null;
//    }
//
//    private boolean isValueConsistent(String variable, Meal value, Map<String, Meal> assignment) {
//        for (Constraint constraint : constraints) {
//            if (!constraint.isSatisfied(variable, value, assignment)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
// 
//}
