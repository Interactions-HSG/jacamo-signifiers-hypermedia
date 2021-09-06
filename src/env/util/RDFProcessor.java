package util;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.SimpleIRI;

public class RDFProcessor {

    public static int getIntFromValue(Value value){
        Literal l = (Literal) value;
        return l.intValue();
    }

    public static int getLastInt(SimpleIRI v){
        System.out.println("get last int");
        String s = v.stringValue();
        System.out.println("string value");
        String str = s.substring(s.length()-1);
        System.out.println(str);
        int n = Integer.parseInt(str);
        return n;
    }
}
