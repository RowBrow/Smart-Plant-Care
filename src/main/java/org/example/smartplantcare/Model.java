package org.example.smartplantcare;
import java.util.*;

class Model{
  List<String> baseProgram(){return Arrays.asList("NatBach","HumTek");}
  List<String> subjectModule(){return Arrays.asList("Computer Science","Informatik","Astrology");}
  List<String> baseCourse(String base){
    if(base.equals("NatBach")) {
      return Arrays.asList(
          "BK1 Empirical Data",
          "BK2 Experimental Methods",
          "BK3 Theory of Natural Science",
          "Logic and Discrete Mathematics",
          "Functional Biology – Zoology",
          "Linear Algebra",
          "Organic Chemistry",
          "Biological Chemistry",
          "Statistical Models",
          "Functional Programming and Language Implementations",
          "Classical Mechanics",
          "Environmental Science",
          "Cell Biology",
          "Functional biology – Botany",
          "Supplementary Physics",
          "Calculus",
          "The Chemical Reaction",
          "Scientific Computing",
          "Energy and Climate Changes"
          );
      }
      if(base.equals("HumTek")){
        return Arrays.asList(
          "Design og Konstruktion I+Workshop" ,
          "Subjektivitet, Teknologi og Samfund I" ,
          "Teknologiske systemer og artefakter I" ,
          "Videnskabsteori" ,
          "Design og Konstruktion II+Workshop" ,
          "Subjektivitet, Teknologi og Samfund II" ,
          "Bæredygtige teknologier" ,
          "Kunstig intelligens" ,
          "Medier og teknologi - datavisualisering" ,
          "Teknologiske Systemer og Artefakter II - Sundhedsteknologi" ,
          "Den (in)humane storby" ,
          "Interactive Design in the Field" ,
          "Organisation og ledelse af designprocesser"
      );
    }
    return null;
  }
  List<String> baseProject(String base) {
    return Arrays.asList("BP1 " + base, "BP2 " + base, "BP3 " + base, "Bachelorproject " + base);
  }
  List<String> subjectCourse(String base) {
    if (base.equals("Computer Science")) {
      return Arrays.asList("Essential Computing",
          "Software Development","Interactive Digital Systems" );
    }
    if (base.equals("Informatik")) {
      return Arrays.asList("Organisatorisk forandring og IT",
          "BANDIT","Web baserede IT-Systemer" );
    }
    if (base.equals("Astrology")) {
      return Arrays.asList("Essential Astrology",
          "Venus studies","Mars studies","Ascendant calculations" );
    }
    return null;
  }
  String subjectProject(String subject) {
    return "Subject module project in "+subject;
  }
  int courseWeight(String course){
    if(course.equals("Software Development"))return 10;
    if(course.equals("BANDIT"))return 10;
    return 5;
  }
  boolean isProject(String s){
    for(String fm:subjectModule())if(s.equals(subjectProject(fm))) return true;
    for(String bs:baseProgram())if(baseProject(bs).contains(s))return true;
    return false;
  }
}
