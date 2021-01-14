package org.openmrs.module.ehrlaboratory.util;

import java.util.Arrays;
import java.util.List;

public class LaboratoryMetadata {

    //Investigations
    public static String SEROLOGY = "SEROLOGY";
    public static String BIOCHEMISTRY = "BIOCHEMISTRY";

    //confidential investigations
    public static String TEST = "TEST";
    public static String TEST1 = "TEST1";


    public static List<String> allInvestigations() {
        return Arrays.asList(SEROLOGY, BIOCHEMISTRY);
    }

    public static List<String> allConfidentialInvestigations() {
        return Arrays.asList(TEST, TEST1);
    }



}
