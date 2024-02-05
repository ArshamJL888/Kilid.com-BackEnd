package Main.DAO.repos;

import Main.classes.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PropertyRepo extends JpaRepository<Property, Long> {


    @Query("select p from Property p where TYPE (p)=PropertyForSale")
    public List<PropertyForSale> getPropertyForSale();

    @Query("select p from Property p where TYPE (p)=PropertyForMortgage ")
    public List<PropertyForMortgage> getPropertyForMortgage();

    @Query("select p from Property p where TYPE (p)=PropertyForRent ")
    public List<PropertyForRent> getPropertyForRent();

    @Modifying
    @Query("update PropertyForSale p set p.age=:#{#pr.age}" +
            ",p.agencyID=:#{#pr.agencyID},p.area=:#{#pr.area}" +
            ",p.city=:#{#pr.city},p.description=:#{#pr.description}," +
            "p.numberOfRoom=:#{#pr.numberOfRoom},p.sellCost=:#{#pr.sellCost}" +
            ", p.title=:#{#pr.title},p.usage=:#{#pr.usage},p.zone=:#{#pr.zone} where p.propertyId=:#{#pr.propertyId}  ")
    public void updatePropertyForSale(PropertyForSale pr);


    @Modifying
    @Query("update PropertyForMortgage p set p.age=:#{#pr.age}" +
            ",p.agencyID=:#{#pr.agencyID},p.area=:#{#pr.area}" +
            ",p.city=:#{#pr.city},p.description=:#{#pr.description}," +
            "p.numberOfRoom=:#{#pr.numberOfRoom},p.mortgageCost=:#{#pr.mortgageCost}" +
            ", p.title=:#{#pr.title},p.usage=:#{#pr.usage},p.zone=:#{#pr.zone} where p.propertyId=:#{#pr.propertyId}")
    public void updatePropertyForMortgage(PropertyForMortgage pr);


    @Modifying
    @Query("update PropertyForRent p set p.age=:#{#pr.age}" +
            ",p.agencyID=:#{#pr.agencyID},p.area=:#{#pr.area}" +
            ",p.city=:#{#pr.city},p.description=:#{#pr.description}," +
            "p.numberOfRoom=:#{#pr.numberOfRoom},p.preCost=:#{#pr.monthlyRent},p.preCost=:#{#pr.preCost} " +
            ", p.title=:#{#pr.title},p.usage=:#{#pr.usage},p.zone=:#{#pr.zone} where p.propertyId=:#{#pr.propertyId}")
    public void updatePropertyForRent(PropertyForRent pr);


    @Query("SELECT p FROM Property p " +
            "JOIN PropertyCondition pc ON p.propertyId = pc.propertyId " +
            "JOIN PropertyFacility pf ON p.propertyId = pf.propertyId " +
            "WHERE " +
            " ( :#{#pcInput.convertible} = '0' OR pc.convertible = :#{#pcInput.convertible} ) " +
            "AND ( :#{#pcInput.presale} = '0' OR pc.presale = :#{#pcInput.presale} ) " +
            "AND ( :#{#pcInput.buildingLocation} = '0' OR pc.buildingLocation = :#{#pcInput.buildingLocation} ) " +
            "AND ( :#{#pcInput.loan} = '0' OR pc.loan = :#{#pcInput.loan} )" +
            "AND ( :#{#pcInput.newlyBuilt} = '0' OR pc.newlyBuilt = :#{#pcInput.newlyBuilt} ) " +
            "AND ( :#{#pcInput.equity} = '0' OR pc.equity = :#{#pcInput.equity} ) " +
            "AND ( :#{#pcInput.shoppingCenter} = '0' OR pc.shoppingCenter = :#{#pcInput.shoppingCenter} ) " +
            "AND ( :#{#pcInput.mall} = '0' OR pc.mall = :#{#pcInput.mall} ) " +
            "AND ( :#{#pfInput.parking} = '0' OR pf.parking = :#{#pfInput.parking} ) " +
            "AND ( :#{#pfInput.lobby} = '0' OR pf.lobby = :#{#pfInput.lobby} ) " +
            "AND ( :#{#pfInput.elevator} = '0' OR pf.elevator = :#{#pfInput.elevator} ) " +
            "AND ( :#{#pfInput.pool} = '0' OR pf.pool = :#{#pfInput.pool} ) " +
            "AND ( :#{#pfInput.sauna} = '0' OR pf.sauna = :#{#pfInput.sauna} ) " +
            "AND ( :#{#pfInput.gym} = '0' OR pf.gym = :#{#pfInput.gym} ) " +
            "AND ( :#{#pfInput.buildingGuard} = '0' OR pf.buildingGuard = :#{#pfInput.buildingGuard} ) " +
            "AND ( :#{#pfInput.balcony} = '0' OR pf.balcony = :#{#pfInput.balcony} ) " +
            "AND ( :#{#pfInput.rooftopGarden} = '0' OR pf.rooftopGarden = :#{#pfInput.rooftopGarden} ) " +
            "AND ( :#{#pfInput.airCondition} = '0' OR pf.airCondition = :#{#pfInput.airCondition} ) " +
            "AND ( :#{#pfInput.conferenceHall} = '0' OR pf.conferenceHall = :#{#pfInput.conferenceHall} ) " +
            "AND ( :#{#pfInput.jacuzzi} = '0' OR pf.jacuzzi = :#{#pfInput.jacuzzi} ) " +
            "AND ( :#{#pfInput.centralAntenna} = '0' OR pf.centralAntenna = :#{#pfInput.centralAntenna} ) " +
            "AND ( :#{#pfInput.remoteControlledDoor} = '0' OR pf.remoteControlledDoor = :#{#pfInput.remoteControlledDoor} ) " +
            "AND ( :#{#pcInput.cooperative} = '0' OR pc.cooperative = :#{#pcInput.cooperative} ) " +
            "AND ( :#{#pcInput.barter} = '0' OR pc.barter = :#{#pcInput.barter} ) " +
            "And p.numberOfRoom >= :numberOfRooms and p.age>=:minAge and p.age<=:maxAge " +
            "and p.area>=:minArea and p.area<=:maxArea and lower(p.usage)=lower(:usage) " +
            "and lower(p.zone) like lower(concat('%',:zone,'%')) and lower(p.city) like lower(concat('%',:city,'%')) and type(p)=:pType ")
    List<Property> joinOnPropertyConditionAndFacilities(PropertyCondition pcInput,
                                                        PropertyFacility pfInput, Long minAge, Long maxAge
            , Long numberOfRooms, Integer minArea, Integer maxArea,
                                                        String usage, String zone, String city, Class pType);

//we will divide every type for now

    @Query("SELECT p FROM Property p " +
            "JOIN PropertyCondition pc ON p.propertyId = pc.propertyId " +
            "JOIN PropertyFacility pf ON p.propertyId = pf.propertyId ")
    List<Property> showAllProperties();

    @Query("SELECT p FROM PropertyForSale p " +
            "JOIN PropertyCondition pc ON p.propertyId = pc.propertyId " +
            "JOIN PropertyFacility pf ON p.propertyId = pf.propertyId " +
            "WHERE " +
            " ( :#{#pcInput.convertible} = '0' OR pc.convertible = :#{#pcInput.convertible} ) " +
            "AND ( :#{#pcInput.presale} = '0' OR pc.presale = :#{#pcInput.presale} ) " +
            "AND ( :#{#pcInput.buildingLocation} = '0' OR pc.buildingLocation = :#{#pcInput.buildingLocation} ) " +
            "AND ( :#{#pcInput.loan} = '0' OR pc.loan = :#{#pcInput.loan} )" +
            "AND ( :#{#pcInput.newlyBuilt} = '0' OR pc.newlyBuilt = :#{#pcInput.newlyBuilt} ) " +
            "AND ( :#{#pcInput.equity} = '0' OR pc.equity = :#{#pcInput.equity} ) " +
            "AND ( :#{#pcInput.shoppingCenter} = '0' OR pc.shoppingCenter = :#{#pcInput.shoppingCenter} ) " +
            "AND ( :#{#pcInput.mall} = '0' OR pc.mall = :#{#pcInput.mall} ) " +
            "AND ( :#{#pfInput.parking} = '0' OR pf.parking = :#{#pfInput.parking} ) " +
            "AND ( :#{#pfInput.lobby} = '0' OR pf.lobby = :#{#pfInput.lobby} ) " +
            "AND ( :#{#pfInput.elevator} = '0' OR pf.elevator = :#{#pfInput.elevator} ) " +
            "AND ( :#{#pfInput.pool} = '0' OR pf.pool = :#{#pfInput.pool} ) " +
            "AND ( :#{#pfInput.sauna} = '0' OR pf.sauna = :#{#pfInput.sauna} ) " +
            "AND ( :#{#pfInput.gym} = '0' OR pf.gym = :#{#pfInput.gym} ) " +
            "AND ( :#{#pfInput.buildingGuard} = '0' OR pf.buildingGuard = :#{#pfInput.buildingGuard} ) " +
            "AND ( :#{#pfInput.balcony} = '0' OR pf.balcony = :#{#pfInput.balcony} ) " +
            "AND ( :#{#pfInput.rooftopGarden} = '0' OR pf.rooftopGarden = :#{#pfInput.rooftopGarden} ) " +
            "AND ( :#{#pfInput.airCondition} = '0' OR pf.airCondition = :#{#pfInput.airCondition} ) " +
            "AND ( :#{#pfInput.conferenceHall} = '0' OR pf.conferenceHall = :#{#pfInput.conferenceHall} ) " +
            "AND ( :#{#pfInput.jacuzzi} = '0' OR pf.jacuzzi = :#{#pfInput.jacuzzi} ) " +
            "AND ( :#{#pfInput.centralAntenna} = '0' OR pf.centralAntenna = :#{#pfInput.centralAntenna} ) " +
            "AND ( :#{#pfInput.remoteControlledDoor} = '0' OR pf.remoteControlledDoor = :#{#pfInput.remoteControlledDoor} ) " +
            "AND ( :#{#pcInput.cooperative} = '0' OR pc.cooperative = :#{#pcInput.cooperative} ) " +
            "AND ( :#{#pcInput.barter} = '0' OR pc.barter = :#{#pcInput.barter} ) " +
            "And p.numberOfRoom >= :numberOfRooms and p.age>=:minAge and p.age<=:maxAge " +
            "and p.area>=:minArea and p.area<=:maxArea and lower(p.usage)=lower(:usage) " +
            "and lower(p.zone) like lower(concat('%',:zone,'%')) and lower(p.city) like lower(concat('%',:city,'%')) " +
            "and type(p)=:pType and p.sellCost>=:minSellCost and p.sellCost<=:maxSellCost")
    List<Property> joinOnPropertyConditionForSaleAndFacilities(PropertyCondition pcInput,
                                                               PropertyFacility pfInput, Long minAge, Long maxAge,
                                                               Long numberOfRooms, Integer minArea, Integer maxArea,
                                                               String usage, String zone, String city, Class pType,
                                                               Long minSellCost, Long maxSellCost);

    @Query("SELECT p FROM PropertyForRent p " +
            "JOIN PropertyCondition pc ON p.propertyId = pc.propertyId " +
            "JOIN PropertyFacility pf ON p.propertyId = pf.propertyId " +
            "WHERE " +
            " ( :#{#pcInput.convertible} = '0' OR pc.convertible = :#{#pcInput.convertible} ) " +
            "AND ( :#{#pcInput.presale} = '0' OR pc.presale = :#{#pcInput.presale} ) " +
            "AND ( :#{#pcInput.buildingLocation} = '0' OR pc.buildingLocation = :#{#pcInput.buildingLocation} ) " +
            "AND ( :#{#pcInput.loan} = '0' OR pc.loan = :#{#pcInput.loan} )" +
            "AND ( :#{#pcInput.newlyBuilt} = '0' OR pc.newlyBuilt = :#{#pcInput.newlyBuilt} ) " +
            "AND ( :#{#pcInput.equity} = '0' OR pc.equity = :#{#pcInput.equity} ) " +
            "AND ( :#{#pcInput.shoppingCenter} = '0' OR pc.shoppingCenter = :#{#pcInput.shoppingCenter} ) " +
            "AND ( :#{#pcInput.mall} = '0' OR pc.mall = :#{#pcInput.mall} ) " +
            "AND ( :#{#pfInput.parking} = '0' OR pf.parking = :#{#pfInput.parking} ) " +
            "AND ( :#{#pfInput.lobby} = '0' OR pf.lobby = :#{#pfInput.lobby} ) " +
            "AND ( :#{#pfInput.elevator} = '0' OR pf.elevator = :#{#pfInput.elevator} ) " +
            "AND ( :#{#pfInput.pool} = '0' OR pf.pool = :#{#pfInput.pool} ) " +
            "AND ( :#{#pfInput.sauna} = '0' OR pf.sauna = :#{#pfInput.sauna} ) " +
            "AND ( :#{#pfInput.gym} = '0' OR pf.gym = :#{#pfInput.gym} ) " +
            "AND ( :#{#pfInput.buildingGuard} = '0' OR pf.buildingGuard = :#{#pfInput.buildingGuard} ) " +
            "AND ( :#{#pfInput.balcony} = '0' OR pf.balcony = :#{#pfInput.balcony} ) " +
            "AND ( :#{#pfInput.rooftopGarden} = '0' OR pf.rooftopGarden = :#{#pfInput.rooftopGarden} ) " +
            "AND ( :#{#pfInput.airCondition} = '0' OR pf.airCondition = :#{#pfInput.airCondition} ) " +
            "AND ( :#{#pfInput.conferenceHall} = '0' OR pf.conferenceHall = :#{#pfInput.conferenceHall} ) " +
            "AND ( :#{#pfInput.jacuzzi} = '0' OR pf.jacuzzi = :#{#pfInput.jacuzzi} ) " +
            "AND ( :#{#pfInput.centralAntenna} = '0' OR pf.centralAntenna = :#{#pfInput.centralAntenna} ) " +
            "AND ( :#{#pfInput.remoteControlledDoor} = '0' OR pf.remoteControlledDoor = :#{#pfInput.remoteControlledDoor} ) " +
            "AND ( :#{#pcInput.cooperative} = '0' OR pc.cooperative = :#{#pcInput.cooperative} ) " +
            "AND ( :#{#pcInput.barter} = '0' OR pc.barter = :#{#pcInput.barter} ) " +
            "And p.numberOfRoom >= :numberOfRooms and p.age>=:minAge and (p.age<=:maxAge or :maxAge=0) " +
            "and p.area>=:minArea and (p.area<=:maxArea or :maxArea=0) and (lower(p.usage)=lower(:usage) or :usage='') " +
            "and (lower(p.zone) like lower(concat('%',:zone,'%')) or :zone='') and (lower(p.city) like lower(concat('%',:city,'%')) or :city='') " +
            "and p.monthlyRent>=:minMonthlyRent and (p.monthlyRent<=:maxMonthlyRent or :maxMonthlyRent=0 ) " +
            "and p.preCost>=:minPreCostMortgage and (p.preCost<=:maxPreCostMortgage or :maxPreCostMortgage=0)")
    List<Property> joinOnPropertyConditionForRentAndFacilities(PropertyCondition pcInput,
                                                               PropertyFacility pfInput, Long minAge, Long maxAge,
                                                               Long numberOfRooms, Integer minArea, Integer maxArea,
                                                               String usage, String zone, String city,
                                                               Long minPreCostMortgage, Long maxPreCostMortgage,
                                                               Long minMonthlyRent, Long maxMonthlyRent);

    @Query("SELECT p FROM PropertyForMortgage p " +
            "JOIN PropertyCondition pc ON p.propertyId = pc.propertyId " +
            "JOIN PropertyFacility pf ON p.propertyId = pf.propertyId " +
            "WHERE " +
            " ( :#{#pcInput.convertible} = '0' OR pc.convertible = :#{#pcInput.convertible} ) " +
            "AND ( :#{#pcInput.presale} = '0' OR pc.presale = :#{#pcInput.presale} ) " +
            "AND ( :#{#pcInput.buildingLocation} = '0' OR pc.buildingLocation = :#{#pcInput.buildingLocation} ) " +
            "AND ( :#{#pcInput.loan} = '0' OR pc.loan = :#{#pcInput.loan} )" +
            "AND ( :#{#pcInput.newlyBuilt} = '0' OR pc.newlyBuilt = :#{#pcInput.newlyBuilt} ) " +
            "AND ( :#{#pcInput.equity} = '0' OR pc.equity = :#{#pcInput.equity} ) " +
            "AND ( :#{#pcInput.shoppingCenter} = '0' OR pc.shoppingCenter = :#{#pcInput.shoppingCenter} ) " +
            "AND ( :#{#pcInput.mall} = '0' OR pc.mall = :#{#pcInput.mall} ) " +
            "AND ( :#{#pfInput.parking} = '0' OR pf.parking = :#{#pfInput.parking} ) " +
            "AND ( :#{#pfInput.lobby} = '0' OR pf.lobby = :#{#pfInput.lobby} ) " +
            "AND ( :#{#pfInput.elevator} = '0' OR pf.elevator = :#{#pfInput.elevator} ) " +
            "AND ( :#{#pfInput.pool} = '0' OR pf.pool = :#{#pfInput.pool} ) " +
            "AND ( :#{#pfInput.sauna} = '0' OR pf.sauna = :#{#pfInput.sauna} ) " +
            "AND ( :#{#pfInput.gym} = '0' OR pf.gym = :#{#pfInput.gym} ) " +
            "AND ( :#{#pfInput.buildingGuard} = '0' OR pf.buildingGuard = :#{#pfInput.buildingGuard} ) " +
            "AND ( :#{#pfInput.balcony} = '0' OR pf.balcony = :#{#pfInput.balcony} ) " +
            "AND ( :#{#pfInput.rooftopGarden} = '0' OR pf.rooftopGarden = :#{#pfInput.rooftopGarden} ) " +
            "AND ( :#{#pfInput.airCondition} = '0' OR pf.airCondition = :#{#pfInput.airCondition} ) " +
            "AND ( :#{#pfInput.conferenceHall} = '0' OR pf.conferenceHall = :#{#pfInput.conferenceHall} ) " +
            "AND ( :#{#pfInput.jacuzzi} = '0' OR pf.jacuzzi = :#{#pfInput.jacuzzi} ) " +
            "AND ( :#{#pfInput.centralAntenna} = '0' OR pf.centralAntenna = :#{#pfInput.centralAntenna} ) " +
            "AND ( :#{#pfInput.remoteControlledDoor} = '0' OR pf.remoteControlledDoor = :#{#pfInput.remoteControlledDoor} ) " +
            "AND ( :#{#pcInput.cooperative} = '0' OR pc.cooperative = :#{#pcInput.cooperative} ) " +
            "AND ( :#{#pcInput.barter} = '0' OR pc.barter = :#{#pcInput.barter} ) " +
            "And p.numberOfRoom >= :numberOfRooms and p.age>=:minAge and (p.age<=:maxAge or :maxAge=0) " +
            "and p.area>=:minArea and (p.area<=:maxArea or :maxArea=0) and (lower(p.usage)=lower(:usage) or :usage='') " +
            "and (lower(p.zone) like lower(concat('%',:zone,'%')) or :zone='') and (lower(p.city) like lower(concat('%',:city,'%')) or :city='') " +
            "and p.mortgageCost>=:minPreCostMortgage and (p.mortgageCost<=:maxPreCostMortgage or :maxPreCostMortgage=0 )")
    List<Property> joinOnPropertyForMortgageConditionAndFacilities(PropertyCondition pcInput,
                                                                   PropertyFacility pfInput, Long minAge, Long maxAge,
                                                                   Long numberOfRooms, Integer minArea, Integer maxArea,
                                                                   String usage, String zone, String city,
                                                                   Long minPreCostMortgage,
                                                                   Long maxPreCostMortgage);

    List<Property> findByAgencyID(Long agencyID);


    @Query("select p from Property p where " +
            "lower(p.zone) like lower(concat('%',:name,'%')) " +
            "OR lower(p.title) like lower(concat('%',:name,'%')) " +
            "OR lower(p.city) like lower(concat('%',:name,'%')) ")
    List<Property> findByName(String name);

}
