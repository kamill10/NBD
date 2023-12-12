package p.lodz.Model.Provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import p.lodz.Model.Client;
import p.lodz.Model.Dao.ClientDao;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;

import java.util.UUID;


public class ClientTypeProvider   {

    CqlSession session ;
    ClientTypeProvider(CqlSession session){
        this.session = session;
    }


    public void create(ClientType clientType) {
        createType(clientType);
    }

    /*public BoundStatement createType(ClientType clientType) {
        switch (clientType.getDiscriminator()) {
            case "premium" -> {
                Premium premium = (Premium) clientType;
                return session.prepare(premiumEntityHelper.insert().build())
                        .bind()
                        .setDouble(CqlIdentifier.fromCql("client_discount"), premium.getClientDiscount())
                        .setInt(CqlIdentifier.fromCql("shorterDeliveryTime;"), premium.getShorterDeliveryTime())
                        .setString(CqlIdentifier.fromCql("discriminator"), premium.getDiscriminator());
            }
            case "standard" -> {
                Standard standard = (Standard) clientType;
                return session.prepare(standardEntityHelper.insert().build())
                        .bind()
                        .setDouble(CqlIdentifier.fromCql("client_discount"), standard.getClientDiscount())
                        .setInt(CqlIdentifier.fromCql("shorterDeliveryTime;"), standard.getShorterDeliveryTime())
                        .setString(CqlIdentifier.fromCql("discriminator"), standard.getDiscriminator());
            }
            case "deluxe" -> {
                PremiumDeluxe deluxe = (PremiumDeluxe) clientType;
                return session.prepare(premiumEntityHelper.insert().build())
                        .bind()
                        .setDouble(CqlIdentifier.fromCql("client_discount"), deluxe.getClientDiscount())
                        .setInt(CqlIdentifier.fromCql("shorterDeliveryTime;"), deluxe.getShorterDeliveryTime())
                        .setString(CqlIdentifier.fromCql("discriminator"), deluxe.getDiscriminator());
            }
            default -> throw new IllegalArgumentException("Unknown client type discriminator: " + clientType.getDiscriminator());
        }
    } */
    public void  createType(ClientType clientType) {
        String insertQuery = "INSERT INTO shop.clienttype (id, client_discount, shorter_delivery_time, discriminator) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = session.prepare(insertQuery);
        switch (clientType.getDiscriminator()) {
            case "premium" -> {
                Premium premium = (Premium) clientType;
                session.execute(preparedStatement.bind(premium.getId(), premium.getClientDiscount(), premium.getShorterDeliveryTime(), premium.getDiscriminator()));
            }
            case "standard" -> {
                Standard standard = (Standard) clientType;
                session.execute(preparedStatement.bind(standard.getId(), standard.getClientDiscount(), standard.getShorterDeliveryTime(), standard.getDiscriminator()));
            }
            case "deluxe" -> {
                PremiumDeluxe deluxe = (PremiumDeluxe) clientType;
                session.execute(preparedStatement.bind(deluxe.getId(), deluxe.getClientDiscount(), deluxe.getShorterDeliveryTime(),deluxe.getDiscriminator()));

            }
            default -> throw new IllegalArgumentException("Unknown client type discriminator: " + clientType.getDiscriminator());
        }
    }



}
