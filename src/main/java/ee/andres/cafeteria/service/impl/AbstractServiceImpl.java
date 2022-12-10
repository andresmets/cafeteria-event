package ee.andres.cafeteria.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.util.*;

public abstract class AbstractServiceImpl<T> {

      public JsonNode responseNode(List<T> objects){
        ArrayNode node = JsonNodeFactory.instance.arrayNode();
        for(T t : objects){
            node.addPOJO(t);
        }
        return node;
    }
    public JsonNode responseNode(Map<Long, Integer> objects){
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        Iterator<Long> it = objects.keySet().iterator();
        while(it.hasNext()){
            Long key = it.next();
            node.put(key.toString(), objects.get(key));
        }
        return node;
    }
    public JsonNode responseLabels(List<String> keys, Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("labels", locale);
        ObjectNode labels = JsonNodeFactory.instance.objectNode();
        for(String key : keys){
            labels.put(key, bundle.getString(key));
        }
        return labels;
    }

    public ObjectNode responseNode(List<String> keys, List<Object> values){
        if(keys.size() != values.size()){
            throw new IllegalArgumentException("keys and values must equal");
        }
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        for(int i=0; i< keys.size(); i++){
            if(values.get(i)  instanceof List<?>){
                ArrayNode array = node.putArray(keys.get(i));
                for(Object o : (List)values.get(i)){
                    array.addPOJO(o);
                }
            }
            else{
                ValueNode value = JsonNodeFactory.instance.pojoNode(values.get(i));
                node.set(keys.get(i), value);
            }
        }
        return node;
    }
}
