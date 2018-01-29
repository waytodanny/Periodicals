package com.periodicals.utils.uuid;

import com.fasterxml.uuid.Generators;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for uuid objects generation
 */
public class UUIDHelper {
    public static UUID generateSequentialUuid() {
        return Generators.timeBasedGenerator().generate();
    }

    public static boolean isUUID(String str){
        Pattern pattern = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
