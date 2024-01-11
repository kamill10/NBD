package org.example;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.exceptions.MongoDBException;

public class MessageSaver {
    private final MongoCollection<Document> mongoCollection;

    public MessageSaver() {

        try  {
            MongoRepo repo = new MongoRepo();
            this.mongoCollection = repo.getDatabase().getCollection("rent-message-db");
        } catch (MongoException e) {
            throw new MongoDBException("Error while creating database");
        }
    }


    public void saveToMongo(String message){
        Document jsonDocument = new Document()
                .append("rent", message);
        mongoCollection.insertOne(jsonDocument);

    }
}
