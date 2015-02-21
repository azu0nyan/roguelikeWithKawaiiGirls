package roguelike.editor;

import it.biobytes.ammentos.PersistenceException;
import roguelike.CONFIGURATION;
import roguelike.DAO.BodiesLoader;
import roguelike.DAO.BodyPartsLoader;
import roguelike.DAO.CreaturesLoader;
import roguelike.DAO.TileTypesLoader;
import roguelike.DAO.prototypes.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.List;

/**
 * @author azu
 *
 *
 */
public class EditorFrame {
    private JTabbedPane tabsPanel;
    private JPanel MainPanel;
    private JPanel creaturesEditor;

    private JPanel tilesEditor;
    private JButton loadTiles;
    private JButton addTileTypeButton;
    private JButton saveTileTypeButton;
    private JButton removeTileTypeButton;
    private JList<TileType> tilesList;
    private JCheckBox isWalkableOverField;
    private JCheckBox idLadderField;
    private JCheckBox isWalkableField;
    private JTextField charColorField;
    private JTextField colorField;
    private JTextField symbolField;
    private JTextField nameField;
    private JTextField idField;
    private JPanel BodiesEditor;
    private JButton loadBodiesFromDB;
    private JButton addBody;
    private JButton saveBodiesToDB;
    private JButton removeBody;
    private JList<BodyPrototype> bodiesList;
    private JTextField bodyId;
    private JTextField bodyName;
    private JTextField maxBlood;
    private JTextField bloodRegeneration;
    private JTextField mainBodyPart;
    private JButton loadCreatures;
    private JButton saveCreatures;
    private JButton removeCreature;
    private JButton addCreature;
    private JList<CreaturePrototype> creaturesList;
    private JTextField creatureID;
    private JTextField creatureName;
    private JTextField creatureBody;
    private JTextField creatureSize;
    private JTextField creatureAI;
    private JTextField stats;
    private JTextField creatureColor;
    private JTextField creatureSymbol;
    private JCheckBox isDead;
    private JCheckBox isHumanoid;
    private JTextField creatureFraction;
    private JButton loadBodyParts;
    private JButton removeBodyParts;
    private JButton addBodyParts;
    private JButton saveBodyParts;
    private JTree bodyPartsList;
    private JList bodyPartTags;
    private JTextField bodyPartID;
    private JTextField bodyPartName;
    private JTextField bodyPartSize;
    private JTextField bodyPartHP;
    private JTextField bodyPartequipmentSlot;
    private JCheckBox bodyPartIsLinked;


    List<TileType> loadedTileTypes;
    DefaultListModel<TileType> tilesModel;
    TileType currentTile = null;
    TileTypesLoader tileTypesLoader;

    List<BodyPrototype> loadedBodyPrototypes;
    DefaultListModel<BodyPrototype> bodiesModel;
    BodyPrototype currentBody = null;
    BodiesLoader bodiesLoader;

    List<CreaturePrototype> loadedCreatures;
    DefaultListModel<CreaturePrototype> creaturesModel;
    CreaturePrototype currentCreature = null;
    CreaturesLoader creaturesLoader;

    List<BodyPartPrototype> loadedBodyPartPrototypes;
    TreeModel bodyPartsModel;
    DefaultMutableTreeNode bodyPartsRoot;
    BodyPartPrototype currentBodyPart = null;
    BodyPartsLoader bodyPartsLoader;

    public static void main(String[] args) {
        JFrame frame = new JFrame("EditorFrame");
        frame.setContentPane(new EditorFrame().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public EditorFrame() {
        setupTilesEditor();
        setupBodyEditor();
        setupCreaturesEditor();
        setupBodyPartsEditor();
    }

    public void setupBodyPartsEditor(){
        loadBodyParts.addActionListener(e -> {
            bodyPartsLoader = new BodyPartsLoader();
            try {
                bodyPartsLoader.loadBodyPartsTypesFromDB(CONFIGURATION.dbName);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            loadedBodyPartPrototypes = bodyPartsLoader.getPrototypes();
            fillBodyPartsList();
        });
        saveBodyParts.addActionListener(e -> {
            if(bodyPartsModel == null){
                return;
            }
            switchBodyParts();
            for(int i = 0; i < bodyPartsModel.getChildCount(bodyPartsModel.getRoot()); i++){
                try {
                    saveConnectedBodyPartRecursively((BodyPartPrototype)bodyPartsModel.getChild(bodyPartsModel.getRoot(), i));
                } catch (PersistenceException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void setupCreaturesEditor(){
        loadCreatures.addActionListener(e -> {
            creaturesLoader = new CreaturesLoader();
            try {
                creaturesLoader.loadCreaturesFromDB(CONFIGURATION.dbName);
            } catch (PersistenceException e1) {
                e1.printStackTrace();
            }
            loadedCreatures = creaturesLoader.getPrototypes();
            fillCreaturesList();
        });

        saveCreatures.addActionListener(e -> {
            if(creaturesModel == null){
                return;
            }
            switchCreatures();
            for(int i = 0; i < creaturesModel.size(); i++){
                try {
                    creaturesLoader.savePrototype(creaturesModel.get(i));
                } catch (PersistenceException e1) {
                    e1.printStackTrace();
                }
            }
        });
        removeCreature.addActionListener(e -> {
            CreaturePrototype creatureToRemove = creaturesList.getSelectedValue();
            try {
                creaturesLoader.remove(creatureToRemove);
            } catch (PersistenceException e1) {
                e1.printStackTrace();
            }
            creaturesModel.removeElement(creatureToRemove);
            currentCreature = null;
            switchCreatures();
        });

        addCreature.addActionListener(e -> {
            int nextID = creaturesLoader.getNextID();
            CreaturePrototype newCreaturePrototype = new CreaturePrototype();
            newCreaturePrototype.setId(nextID);
            newCreaturePrototype.setName("");
            newCreaturePrototype.setBody(0);
            newCreaturePrototype.setSize(0);
            newCreaturePrototype.setAiType("");
            newCreaturePrototype.setSymbol("");
            newCreaturePrototype.setColor("");
            newCreaturePrototype.setDead(false);
            newCreaturePrototype.setHumanoid(false);
            creaturesModel.addElement(newCreaturePrototype);
        });

        creaturesList.addListSelectionListener(e -> switchCreatures());
    }

    public void setupBodyEditor(){
        loadBodiesFromDB.addActionListener(e -> {
            bodiesLoader = new BodiesLoader();
            try {
                bodiesLoader.loadBodiesFromDB(CONFIGURATION.dbName);
            } catch (PersistenceException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            loadedBodyPrototypes = bodiesLoader.getPrototypes();
            fillBodiesList();
        });

        saveBodiesToDB.addActionListener(e -> {
            if(bodiesModel == null){
                return;
            }
            switchBodies();
            for(int i = 0; i < bodiesModel.size(); i++){
                try {
                    bodiesLoader.savePrototype(bodiesModel.get(i));
                } catch (PersistenceException e1) {
                    e1.printStackTrace();
                }
            }
        });
        removeBody.addActionListener(e -> {
            BodyPrototype bodyToRemove = bodiesList.getSelectedValue();
            try {
                bodiesLoader.remove(bodyToRemove);
            } catch (PersistenceException e1) {
                e1.printStackTrace();
            }
            bodiesModel.removeElement(bodyToRemove);
            currentBody = null;
            switchBodies();
        });

        addBody.addActionListener(e -> {
            int nextID = bodiesLoader.getNextID();
            BodyPrototype newBodyPrototype = new BodyPrototype();
            newBodyPrototype.setId(nextID);
            newBodyPrototype.setName("");
            newBodyPrototype.setBloodRegeneration(0);
            newBodyPrototype.setMainBodyPart(0);
            newBodyPrototype.setMaxBlood(0);
            bodiesModel.addElement(newBodyPrototype);
        });

        bodiesList.addListSelectionListener(e -> {
            switchBodies();
        });
    }

    public void setupTilesEditor(){
        loadTiles.addActionListener(e -> {
            tileTypesLoader = new TileTypesLoader();
            try {
                tileTypesLoader.loadTileTypesFromDB(CONFIGURATION.dbName);
            } catch (PersistenceException e1) {
                e1.printStackTrace();
            }
            loadedTileTypes = tileTypesLoader.getPrototypes();
            fillTileTypeList();
        });

        saveTileTypeButton.addActionListener(e -> {
            if(tilesModel == null){
                return;
            }
            switchTileTypes();
            for(int i = 0; i < tilesModel.size(); i++){
                try {
                    tileTypesLoader.savePrototype(tilesModel.get(i));
                } catch (PersistenceException e1) {
                    e1.printStackTrace();
                }
            }
        });

        removeTileTypeButton.addActionListener(e -> {
            TileType tileToRemove = tilesList.getSelectedValue();
            try {
                tileTypesLoader.remove(tileToRemove);
            } catch (PersistenceException e1) {
                e1.printStackTrace();
            }
            tilesModel.removeElement(tileToRemove);
            currentTile = null;
            switchTileTypes();
        });

        addTileTypeButton.addActionListener(e -> {
            int nextID = tileTypesLoader.getNextID();
            TileType newTileType = new TileType();
            newTileType.setId(nextID);
            newTileType.setSymbol(' ');
            newTileType.setName("new tile");
            newTileType.setCharColor("");
            newTileType.setColor("");
            tilesModel.addElement(newTileType);
        });

        tilesList.addListSelectionListener(e -> {
            switchTileTypes();
        });
    }

    public void switchTileTypes(){
        if(currentTile != null){
            getTileTypeData(currentTile);
        }
        currentTile = tilesList.getSelectedValue();
        if(currentTile != null){
            setTileTypeData(currentTile);
        }
    }

    public void switchBodies(){
        if(currentBody != null){
            getBodyData(currentBody);
        }
        currentBody = bodiesList.getSelectedValue();
        if(currentBody != null){
            setBodyData(currentBody);
        }
    }

    public void switchCreatures(){
        if(currentCreature != null){
            getCreatureData(currentCreature);
        }
        currentCreature = creaturesList.getSelectedValue();
        if(currentCreature != null){
            setCreatureData(currentCreature);
        }
    }

    public void switchBodyParts(){
        if(currentBodyPart != null){
            getBodyPartData(currentBodyPart);;
        }
        currentBodyPart = (BodyPartPrototype) bodyPartsList.getLastSelectedPathComponent();
        if(currentBodyPart != null){
            setBodyPartData(currentBodyPart);
        }
    }


    public void fillTileTypeList(){
        tilesModel = new DefaultListModel<>();
        tilesList.setModel(tilesModel);
        loadedTileTypes.stream().forEach(tilesModel::addElement);
    }

    public void fillBodiesList() {
        bodiesModel = new DefaultListModel<>();
        bodiesList.setModel(bodiesModel);
        loadedBodyPrototypes.stream().forEach(bodiesModel::addElement);
    }

    private void fillCreaturesList() {
        creaturesModel = new DefaultListModel<>();
        creaturesList.setModel(creaturesModel);
        loadedCreatures.stream().forEach(creaturesModel::addElement);
    }

    private void fillBodyPartsList() {
        bodyPartsRoot = new DefaultMutableTreeNode("body parts");
        bodyPartsModel = new DefaultTreeModel(bodyPartsRoot);
        bodyPartsList.setModel(bodyPartsModel);
        for(BodyPartPrototype bodyPart : loadedBodyPartPrototypes){
            if(bodyPart.isMain()){
                DefaultMutableTreeNode main = new DefaultMutableTreeNode(bodyPart);
                bodyPartsRoot.add(main);
                addConnectedBodyPartsRecurcive(main, bodyPart);
            }
        }
    }

    private void addConnectedBodyPartsRecurcive(DefaultMutableTreeNode ownerNode, BodyPartPrototype owner){
        for(ConnectedBodyPartPrototype connection : owner.getConnectedBodyPartPrototypes()){
            BodyPartPrototype connectedPart = bodyPartsLoader.getPrototypeById(connection.getPartId());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(connectedPart);
            ownerNode.add(node);
            addConnectedBodyPartsRecurcive(node, connectedPart);
        }
    }

    private void saveConnectedBodyPartRecursively(BodyPartPrototype owner) throws PersistenceException {
        bodyPartsLoader.savePrototype(owner);
        for(int i = 0; i < bodyPartsModel.getChildCount(owner); i++){
             saveConnectedBodyPartRecursively((BodyPartPrototype) bodyPartsModel.getChild(owner, i));
        }
    }

    public void setTileTypeData(TileType data) {
        charColorField.setText(data.getCharColor());
        colorField.setText(data.getColor());
        symbolField.setText(String.valueOf(data.getSymbol()));
        nameField.setText(data.getName());
        idField.setText(String.valueOf(data.getID()));
        isWalkableField.setSelected(data.isIsWalkable());
        isWalkableOverField.setSelected(data.isIsWalkableOver());
        idLadderField.setSelected(data.isIsLadder());
    }

    public void getTileTypeData(TileType data) {
        data.setCharColor(charColorField.getText());
        data.setColor(colorField.getText());
        data.setSymbol(symbolField.getText().charAt(0));
        data.setName(nameField.getText());
        data.setId(Integer.valueOf(idField.getText()));
        data.setIsWalkable(isWalkableField.isSelected());
        data.setIsWalkableOver(isWalkableOverField.isSelected());
        data.setIsLadder(idLadderField.isSelected());
    }

    public void setBodyData(BodyPrototype data) {
        bodyId.setText(String.valueOf(data.getID()));
        bodyName.setText(data.getName());
        maxBlood.setText(String.valueOf(data.getMaxBlood()));
        bloodRegeneration.setText(String.valueOf(data.getBloodRegeneration()));
        mainBodyPart.setText(String.valueOf(data.getMainBodyPart()));
    }

    public void getBodyData(BodyPrototype data) {
        data.setId(Integer.valueOf(bodyId.getText()));
        data.setName(bodyName.getText());
        data.setMaxBlood(Double.valueOf(maxBlood.getText()));
        data.setBloodRegeneration(Double.valueOf(bloodRegeneration.getText()));
        data.setMainBodyPart(Integer.valueOf(mainBodyPart.getText()));
    }

    public void setCreatureData(CreaturePrototype data) {
        creatureID.setText(String.valueOf(data.getID()));
        creatureName.setText(data.getName());
        creatureBody.setText(String.valueOf(data.getBody()));
        creatureSize.setText(String.valueOf(data.getSize()));
        creatureAI.setText(data.getAiType());
        stats.setText(data.getStats());
        creatureSymbol.setText(String.valueOf(data.getSymbol()));
        creatureFraction.setText(data.getFraction());
        creatureColor.setText(data.getColor());
        isDead.setSelected(data.isDead());
        isHumanoid.setSelected(data.isHumanoid());
    }

    public void getCreatureData(CreaturePrototype data) {
        data.setId(Integer.parseInt(creatureID.getText()));
        data.setName(creatureName.getText());
        data.setBody(Integer.parseInt(creatureBody.getText()));
        data.setSize(Double.parseDouble(creatureSize.getText()));
        data.setAiType(creatureAI.getText());
        data.setStats(stats.getText());
        data.setSymbol(creatureSymbol.getText());
        data.setFraction(creatureFraction.getText());
        data.setColor(creatureColor.getText());
        data.setDead(isDead.isSelected());
        data.setHumanoid(isHumanoid.isSelected());
    }

    public void setBodyPartData(BodyPartPrototype data) {
        bodyPartID.setText(String.valueOf(data.getID()));
        bodyPartName.setText(data.getName());
        bodyPartSize.setText(String.valueOf(data.getSize()));
        bodyPartHP.setText(String.valueOf(data.getMaxHp()));
        bodyPartequipmentSlot.setText(data.getEquipmentSlot());
        bodyPartIsLinked.setSelected(data.isLinked());       //TODO DODELAT
    }

    public void getBodyPartData(BodyPartPrototype data) {
        data.setId(Integer.parseInt(bodyPartID.getText()));
        data.setName(bodyPartName.getText());
        data.setSize(Double.parseDouble(bodyPartSize.getText()));
        data.setMaxHp(Integer.parseInt(bodyPartHP.getText()));
        data.setEquipmentSlot(bodyPartequipmentSlot.getText());
        data.setLinked(bodyPartIsLinked.isSelected());
    }
}
