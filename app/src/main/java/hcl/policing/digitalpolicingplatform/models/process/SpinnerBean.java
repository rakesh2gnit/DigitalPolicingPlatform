package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SpinnerBean implements Serializable {
    @SerializedName("Color")
    @Expose
    private List<Color> color = null;
    @SerializedName("Art")
    @Expose
    private List<Art> art = null;
    @SerializedName("BodyPart")
    @Expose
    private List<BodyPart> bodyPart = null;
    @SerializedName("BuildingMaterial")
    @Expose
    private List<BuildingMaterial> buildingMaterial = null;
    @SerializedName("Caravan")
    @Expose
    private List<Caravan> caravan = null;
    @SerializedName("Clothing")
    @Expose
    private List<Clothing> clothing = null;
    @SerializedName("Document")
    @Expose
    private List<Document> document = null;
    @SerializedName("Drugs")
    @Expose
    private List<Drug> drugs = null;
    @SerializedName("Electrical")
    @Expose
    private List<Electrical> electrical = null;
    @SerializedName("Jewellery")
    @Expose
    private List<Jewellery> jewellery = null;
    @SerializedName("Mobile")
    @Expose
    private List<Mobile> mobile = null;
    @SerializedName("PedalCycle")
    @Expose
    private List<PedalCycle> pedalCycle = null;
    @SerializedName("PlantMachinery")
    @Expose
    private List<PlantMachinery> plantMachinery = null;
    @SerializedName("Weapon")
    @Expose
    private List<Weapon> weapon = null;

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public List<Art> getArt() {
        return art;
    }

    public void setArt(List<Art> art) {
        this.art = art;
    }

    public List<BodyPart> getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(List<BodyPart> bodyPart) {
        this.bodyPart = bodyPart;
    }

    public List<BuildingMaterial> getBuildingMaterial() {
        return buildingMaterial;
    }

    public void setBuildingMaterial(List<BuildingMaterial> buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }

    public List<Caravan> getCaravan() {
        return caravan;
    }

    public void setCaravan(List<Caravan> caravan) {
        this.caravan = caravan;
    }

    public List<Clothing> getClothing() {
        return clothing;
    }

    public void setClothing(List<Clothing> clothing) {
        this.clothing = clothing;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public List<Electrical> getElectrical() {
        return electrical;
    }

    public void setElectrical(List<Electrical> electrical) {
        this.electrical = electrical;
    }

    public List<Jewellery> getJewellery() {
        return jewellery;
    }

    public void setJewellery(List<Jewellery> jewellery) {
        this.jewellery = jewellery;
    }

    public List<Mobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<Mobile> mobile) {
        this.mobile = mobile;
    }

    public List<PedalCycle> getPedalCycle() {
        return pedalCycle;
    }

    public void setPedalCycle(List<PedalCycle> pedalCycle) {
        this.pedalCycle = pedalCycle;
    }

    public List<PlantMachinery> getPlantMachinery() {
        return plantMachinery;
    }

    public void setPlantMachinery(List<PlantMachinery> plantMachinery) {
        this.plantMachinery = plantMachinery;
    }

    public List<Weapon> getWeapon() {
        return weapon;
    }

    public void setWeapon(List<Weapon> weapon) {
        this.weapon = weapon;
    }

    public class Art {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class Color {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class BodyPart {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class BuildingMaterial {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Caravan {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Clothing {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Document {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Drug {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Electrical {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Jewellery {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Mobile {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class PedalCycle {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class PlantMachinery {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public class Weapon {

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}