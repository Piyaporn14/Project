package javaapp;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDB {

    private final MongoClient mongo = new MongoClient("localhost", 27017);
    private final MongoDatabase database = mongo.getDatabase("mongo");
    private final MongoCollection collection = database.getCollection("mongotest");

    public MongoDB() {

        System.out.println("Connect Mongo!");

    }
    public void insertData(Document document) {
          database.getCollection("mongotest").insertOne(document);
    }
        //System.out.println(collection.find());//ให้ปั้นเรียกตัวนี้ไปใช้
//        FindIterable<Document> iterDoc = collection.find();
//        Iterator it = iterDoc.iterator();
//        while (it.hasNext()) {
//            out.print(it.next());
//        }
//        mongo.close();
//    }
}
