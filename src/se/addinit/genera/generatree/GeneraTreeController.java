package se.addinit.genera.generatree;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.GraphItemTypes;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.IMapper;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.Mapper;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.graph.styles.ShinyPlateNodeStyle;
import com.yworks.yfiles.layout.genealogy.FamilyTreeLayout;
import com.yworks.yfiles.layout.genealogy.FamilyTreeLayoutData;
import com.yworks.yfiles.layout.genealogy.FamilyType;
import com.yworks.yfiles.layout.hierarchic.HierarchicLayout;
import com.yworks.yfiles.view.BridgeManager;
import com.yworks.yfiles.view.GraphControl;
import com.yworks.yfiles.view.GraphObstacleProvider;
import com.yworks.yfiles.view.GraphOverviewControl;
import com.yworks.yfiles.view.MouseWheelBehaviors;
import com.yworks.yfiles.view.Pen;
import com.yworks.yfiles.view.input.GraphViewerInputMode;
import com.yworks.yfiles.view.input.MoveInputMode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

public class GeneraTreeController {
	
		public GraphControl graphControl;
		public GraphOverviewControl overviewControl;
		private IMapper<INode, FamilyType> familyTypeMapper;
		 public WebView webView;
		 public VBox vbox;
		 public ImageView imageView;
		 
	  public void initialize() {
		    // Called by the JavaFX framework on loading.
		    IGraph graph = graphControl.getGraph();
		    //webView.getEngine().load("https://www.facebook.com/");
		    
		    //GraphEditorInputMode graphEditorInputMode = new GraphEditorInputMode();
		    
		    GraphViewerInputMode inputMode = new GraphViewerInputMode();
		    // Only nodes are allowed to be focused.
		    inputMode.setSelectableItems(GraphItemTypes.NONE);
		    inputMode.setFocusableItems(GraphItemTypes.NODE);
		    MoveInputMode moveInputMode=new MoveInputMode();
		    inputMode.add(moveInputMode);
		    inputMode.setClickHitTestOrder(
		            new GraphItemTypes[]{
		                    GraphItemTypes.NODE.or(GraphItemTypes.EDGE),
		                    GraphItemTypes.LABEL});
	

		    // register a listener
		    inputMode.addItemLeftClickedListener((src, eventArgs) -> {
		    	if(eventArgs.getItem() instanceof INode){
			      INode hitNode = (INode) eventArgs.getItem();
			      updateSources(hitNode);
		      }
		    });
		    
		    
		 // display tooltips for nodes
		    inputMode.setToolTipItems(GraphItemTypes.NODE);
		    // register a listener
		    inputMode.addQueryItemToolTipListener((src, eventArgs) -> {
		      if (eventArgs.isHandled()) {
		        // A tooltip has already been assigned -> nothing to do.
		        return;
		      }
		      // We can safely cast here because we set ToolTipItems to only Node
		      INode hitNode = (INode) eventArgs.getItem();
		      if (hitNode.getLabels().size() > 0) {
		        eventArgs.setToolTip(new Tooltip(hitNode.getLabels().getItem(0).getText()));
		        // Indicate that the tooltip has been set.
		        eventArgs.setHandled(true);
		      }
		    });
		    
		    graphControl.setInputMode(inputMode);
		    
		    BridgeManager bridgeManager = new BridgeManager();
		    bridgeManager.setCanvasControl(graphControl);
		    bridgeManager.addObstacleProvider(new GraphObstacleProvider());
		    
		    // setup the overview.
		    overviewControl.setGraphControl(graphControl);
		    overviewControl.setHorizontalScrollBarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		    overviewControl.setVerticalScrollBarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		    // set some nice defaults
		    ShinyPlateNodeStyle nodeStyle = new ShinyPlateNodeStyle();
		    nodeStyle.setPaint(Color.LIGHTBLUE);
		    graph.getNodeDefaults().setStyle(nodeStyle);
		    graph.getNodeDefaults().setSize(new SizeD(200, 60));

		    ShinyPlateNodeStyle pinkNodeStyle = new ShinyPlateNodeStyle();
		    pinkNodeStyle.setPaint(Color.LIGHTPINK);
		    
		    ShinyPlateNodeStyle blackNodeStyle = new ShinyPlateNodeStyle();
		    blackNodeStyle.setPaint(Color.BLACK);
		    
		    ShapeNodeStyle groupNodeStyle = new ShapeNodeStyle();
		    groupNodeStyle.setShape(ShapeNodeShape.ROUND_RECTANGLE);
		    groupNodeStyle.setPen(new Pen(Color.DARKBLUE, 2));
		    groupNodeStyle.setPaint(null);
		    graph.getGroupNodeDefaults().setStyle(groupNodeStyle);
		    
		    PolylineEdgeStyle edgeStyle = new PolylineEdgeStyle();
		    edgeStyle.setSmoothingLength(3);
		    //edgeStyle.setSourceArrow(Arrow.CIRCLE);
		    
		    
		    graphControl.getGraph().getEdgeDefaults().setStyle(edgeStyle);
		    
		    graphControl.setMouseWheelBehavior(MouseWheelBehaviors.ZOOM);
		    
		    familyTypeMapper = new Mapper<>(); //maps each node to its type   

		    //JSON
		    
		    HashMap<String, Couple> coupleHashMap= new HashMap<>();
		    
		    List<Person> persons=JSONParser.getPersons();
		    // First create all nodes
		    for(Person person:persons){
		    	INode node = graph.createNode();
		    	person.node=node;
		    	node.setTag(person);
		    	if(person.getSex().equals("MALE"))
		    		familyTypeMapper.setValue(node, FamilyType.MALE);
		    	else{
		    		graph.setStyle(node, pinkNodeStyle);
		    		familyTypeMapper.setValue(node, FamilyType.FEMALE);
		    	}
		    	graph.addLabel(node, person.getFirstName()+" "+person.getLastName()+"\n"+person.getBirthDate()+" - "+person.getDeathDate());
		    }
		    
		    // Create all couples
		    for(Person person:persons){
		    	for(String partnerId:person.partners){
		    		String key="";
		    		if(person.getSex().equals("MALE"))
		    			key=person.id+partnerId;
		    		else
		    			key=partnerId+person.id;
		    		if(coupleHashMap.containsKey(key))
		    			System.out.println("Couple already created");
		    		else{
			    		Person partner=getPersonById(partnerId, persons);
						Couple couple=new Couple();
						couple.setPerson1(person);
						couple.setPerson2(partner);
						INode family = graph.createNode(new RectD(300, 0, 5, 5), blackNodeStyle);
					    familyTypeMapper.setValue(family, FamilyType.FAMILY);
				        graph.createEdge(person.node, family);
				        graph.createEdge(partner.node, family);
				        couple.setFamilyNode(family);
				        coupleHashMap.put(key, couple);
			        }		    				  	
		    	}
		    }
		    
		    
		    for(Person person:persons){
		    	if(person.children.isEmpty()==false){
		    		for(String id:person.children){
		    			Person child=getChild(id, persons);
		    			if(child==null)
		    				System.out.println("Could not find child with id "+id);
		    			else{
		    				if(person.getSex().equals("MALE"))
		    					child.father=person;
		    				else{
		    					child.mother=person;
		    					if(coupleHashMap.containsKey(person.id)){
		    						graph.createEdge(coupleHashMap.get(person.id).getFamilyNode(), child.node);
		    					}
		    					else{
		    						//Find father
		    						Person father=getFatherForChild(child.id, persons);
		    						//Create new Couple and familyNode
		    						if(father==null)
		    							System.out.println("Could not find father!");
		    						else{
		    							String key=father.id+person.id;
		    							Couple couple=coupleHashMap.get(key);
		    							graph.createEdge(couple.getFamilyNode(), child.node);
		    							
//		    							Couple couple=new Couple();
//		    							couple.setPerson1(person);
//		    							couple.setPerson2(father);
//		    							INode family = graph.createNode(new RectD(300, 0, 5, 5), blackNodeStyle);
//		    						    familyTypeMapper.setValue(family, FamilyType.FAMILY);
//		    					        graph.createEdge(person.node, family);
//		    					        graph.createEdge(father.node, family);
//		    					        couple.setFamilyNode(family);
//		    					        coupleHashMap.put(person.id, couple);
//		    					        graph.createEdge(family, child.node);
		    						}
		    					}
		    				}
//		    				graph.createEdge(person.node, child.node);
//		    				System.out.println("Förälder:"+person+" Barn:"+child);
		    			}
		    				
		    		}
		    	}
		    }
		    // END JSON

//		    //Manual
//		    INode family = graph.createNode(new RectD(300, 0, 5, 5), blackNodeStyle);
//		    familyTypeMapper.setValue(family, FamilyType.FAMILY);
//
//		    INode family2 = graph.createNode(new RectD(300, 0, 5, 5), blackNodeStyle);
//		    familyTypeMapper.setValue(family2, FamilyType.FAMILY);
//		    
//	        INode parent1 = graph.createNode();
//	        familyTypeMapper.setValue(parent1, FamilyType.MALE);
//	        INode parent2 = graph.createNode();
//	        familyTypeMapper.setValue(parent2, FamilyType.FEMALE);
//	        INode child1 = graph.createNode();
//	        familyTypeMapper.setValue(child1, FamilyType.MALE);
//	        INode child2 = graph.createNode();
//	        familyTypeMapper.setValue(child2, FamilyType.MALE);
//	        INode child3 = graph.createNode();
//	        familyTypeMapper.setValue(child3, FamilyType.FEMALE);
//	        
//	        INode grandParent1 = graph.createNode();
//	        familyTypeMapper.setValue(grandParent1, FamilyType.MALE);
//	        INode grandParent2 = graph.createNode();
//	        familyTypeMapper.setValue(grandParent2, FamilyType.FEMALE);
//        
//	        graph.setStyle(parent2, pinkNodeStyle);
//	        graph.setStyle(grandParent2, pinkNodeStyle);
//	        graph.setStyle(child2, pinkNodeStyle);
//
//	        ILabel ln1 = graph.addLabel(parent1, "Tommys pappa");
//	        ILabel ln2 = graph.addLabel(parent2, "Tommys mamma");
//	        ILabel ln3 = graph.addLabel(child1, "Tommy");
//	        ILabel ln4 = graph.addLabel(child2, "Tommys syster");
//	        ILabel ln5 = graph.addLabel(child3, "Tommys bortadopterade bror");
// 
//	        graph.addLabel(grandParent1, "Tommys farfar");
//	        graph.addLabel(grandParent2, "Tommys farmor");
//
//	        graph.createEdge(grandParent1, family2);
//	        graph.createEdge(grandParent2, family2);
//	        graph.createEdge(family2, parent1);
//	        graph.createEdge(parent1, family);
//	        graph.createEdge(parent2, family);
//	        graph.createEdge(family, child1);
//	        graph.createEdge(family, child2);
//	        graph.createEdge(family, child3);
//	        // End Manual
	        
		    
		   
	        
	        FamilyTreeLayout familyTreeLayout=new FamilyTreeLayout();
	        familyTreeLayout.setSpacingBetweenFamilyMembers(20);
	        FamilyTreeLayoutData familyTreeLayoutData=new FamilyTreeLayoutData();
	        familyTreeLayoutData.getFamilyTypes().setMapper(familyTypeMapper);
	        graph.applyLayout(familyTreeLayout,familyTreeLayoutData);


	        // fit it nicely into the component
//	        graphControl.setZoom(0.5);
	        graphControl.fitGraphBounds();
//		    graphControl.setZoom(0.5);
//		    graphControl.updateContentRect();
//		    graphControl.setMouseWheelBehavior(MouseWheelBehaviors.ZOOM);
		

		  }



		private Person getPersonById(String partnerId, List<Person> persons) {
			for(Person person:persons){
				if(person.id.equals(partnerId))
							return person;	
			}
			return null;
		}



		private Object updateSources(INode currentItem) {
			System.out.println("Clicked!");
		        Person person=(Person) currentItem.getTag();
		        if(person==null)
		        	return null;
		        System.out.println(person.getImage());
				Image image=new Image(person.getImage());
				imageView.setImage(image);
		        vbox.getChildren().clear();
		        for(Source source:person.sources){
		        	Hyperlink hyperlink=new Hyperlink();
		        	hyperlink.setText(source.getTitle()+","+source.getRepository()+","+source.getPublisher());
		        	hyperlink.setUserData(source);
		        	hyperlink.setOnAction(new EventHandler<ActionEvent>() {
		        	    @Override
		        	    public void handle(ActionEvent e) {
		        	        sourceClicked(hyperlink);
		        	    }
		        	});
		        	vbox.getChildren().add(hyperlink);
		        }
		return null;
	}

		public void sourceClicked(Hyperlink hyperlink){
			Source source=(Source)hyperlink.getUserData();
			String url="https://www.arkivdigital.se/aid/info/"+source.getAid();
			System.out.println(url);
			  try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				} catch (URISyntaxException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		}

		private Person getFatherForChild(String id, List<Person> persons) {
			for(Person person:persons){
				if(person.getSex().equals("MALE"))
					for(String childId:person.children)
						if(id.equals(childId))
							return person;
					
			}
		return null;
	}



		private Person getChild(String id, List<Person> persons) {
			for(Person person:persons){
				if(person.id.equals(id))
					return person;
			}
		return null;
	}



		public void onLoaded() {
		    // Called by our application right after stage is loaded.
			  graphControl.fitGraphBounds();
			  graphControl.setZoom(1);
		  }
		  
		  private HierarchicLayout createLayouter() {
			    HierarchicLayout ihl = new HierarchicLayout();
			    ihl.setOrthogonalRoutingEnabled(true);
			    ihl.setRecursiveGroupLayeringEnabled(true);
			    ihl.setAutomaticEdgeGroupingEnabled(true);
			    return ihl;
			  }
		  
		  /**
		   * Action handler for zoom in button action.
		   */
		  public void handleZoomInAction() {
		    graphControl.setZoom(graphControl.getZoom() * 1.25);
		  }

		  /**
		   * Action handler for zoom out button action.
		   */
		  public void handleZoomOutAction() {
		    graphControl.setZoom(graphControl.getZoom() * 0.8);
		  }

		  /**
		   * Action handler for reset zoom button action.
		   */
		  public void handleResetZoomAction() {
		    graphControl.setZoom(1);
		  }

		  /**
		   * Action handler for fit to content button action.
		   */
		  public void handleFitToContentAction() {
		    graphControl.fitGraphBounds();
		  }
		  
		  public void fristNameClicked(ActionEvent e){
			  System.out.println("Firstname clicked");
			  if(e.getSource() instanceof Hyperlink){
			  try {
				  Hyperlink link=(Hyperlink) e.getSource();
				  System.out.println(link.getText());
					Desktop.getDesktop().browse(new URI("https://app.arkivdigital.se/production/index.html?aid=v18098.b198.s379&page=main/index"));
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				} catch (URISyntaxException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		  }
}
