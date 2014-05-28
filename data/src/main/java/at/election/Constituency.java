package at.election;

public enum Constituency {
    MUMBAI_NORTH("Mumbai North"),
    MUMBAI_NORTH_WEST("Mumbai North West"),
    NASHIK("Nashik"),
    MUMBAI_SOUTH("Mumbai South"),
    RAIGAD("Raigad"),
    AKOLA("Akola"),
    HATKANANGLE("Hatkanangle"),
    WARDHA("Wardha"),
    GADCHIROLI_CHIMUR("Gadchiroli Chimur"),
    DINDORI("Dindori"),
    PARBHANI("Parbhani"),
    SOLAPUR("Solapur"),
    BHIWANDI("Bhiwandi"),
    BARAMATI("Baramati"),
    PUNE("Pune"),
    RAMTEK("Ramtek"),
    MADHA("Madha"),
    PALGHAR("Palghar"),
    MUMBAI_NORTH_EAST("Mumbai North East"),
    SATARA("Satara"),
    RATNAGIRI_SINDHUDURG("Ratnagiri Sindhudurg"),
    THANE("Thane"),
    DHULE("Dhule"),
    BULDHANA("Buldhana"),
    JALGAON("Jalgaon"),
    AURANGABAD("Aurangabad"),
    SHIRDI("Shirdi"),
    MUMBAI_NORTH_CENTRAL("Mumbai North central"),
    KALYAN("Kalyan"),
    MUMBAI_SOUTH_CENTRAL("Mumbai South central"),
    AHMADNAGAR("Ahmadnagar"),
    YAVATMAL_WASHIM("Yavatmal Washim"),
    RAVER("Raver"),
    OSMANABAD("Osmanabad"),
    SHIRUR("Shirur"),
    LATUR("Latur"),
    NANDURBAR("Nandurbar"),
    JALNA("Jalna"),
    MAVAL("Maval"),
    SANGLI("Sangli"),
    BHANDARA_GONDIYA("Bhandara Gondiya"),
    KOLHAPUR("Kolhapur"),
    NAGPUR("Nagpur"),
    AMRAVATI("Amravati"),
    CHANDRAPUR("Chandrapur"),
    BEED("Beed"),
    HINGOLI("Hingoli"),
    NANDED("Nanded");

    public final String name;

    Constituency(String name) {
        this.name = name;
    }
}
