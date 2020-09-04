import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private final Model currentModel  = new Model();

    @FXML
    private TextField TextFieldOfSize;
    @FXML
    private GridPane ContentGrid;

    @FXML
    private Label MinLable;
    @FXML
    private Label MaxLable;

    @FXML
    public void GenerateMatrix()
    {
        try
        {
            int size = Integer.parseInt(TextFieldOfSize.getText());
            currentModel.GenerateMatrix(size, 0,99);
            TextFieldOfSize.setText(String.valueOf(currentModel.Size));
            currentModel.FindValues();
            AdaptMatrix();
            UpdateViews();

        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            StringBuilder digits = new StringBuilder();
            for (char symbol : TextFieldOfSize.getText().toCharArray())
                if(Character.isDigit(symbol))
                    digits.append(symbol);
                String clearString = digits.toString();
            TextFieldOfSize.setText(clearString.equals("") ? "0" : clearString);

        }
    }


    @FXML
    public void SwitchValues()
    {
        currentModel.SwitchValues();
        AdaptMatrix();
        UpdateViews();
    }

    private void UpdateViews()
    {
        String[] result = currentModel.GetMatrixResults();
        MinLable.setText(result[0]);
        MaxLable.setText(result[1]);
    }

    private void AdaptMatrix()
    {
        MatrixNode[][] rawMatrix = currentModel.DrawMatrix(true);

        ContentGrid.getChildren().clear();

        for (int y = 0; y < rawMatrix.length; y++)
        {
            for (int x = 0; x < rawMatrix.length; x++)
            {
                Label node = new Label();
                node.setId(rawMatrix[y][x].elementType.name());

                node.setText(String.valueOf(rawMatrix[y][x].value));

                Font calculatingFont = new Font("Roboto Light",Math.min(ContentGrid.getHeight(), ContentGrid.getWidth())/rawMatrix.length/2);
                node.setFont(calculatingFont);

                node.setPrefHeight(1000000);
                node.setPrefWidth(1000000);
                node.setAlignment(Pos.CENTER);

                ContentGrid.setRowIndex(node, y);
                ContentGrid.setColumnIndex(node, x);
                ContentGrid.getChildren().add(node);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->
        {
            GenerateMatrix();

            InvalidationListener watcher = (observable) ->
            {
                Platform.runLater(()->
                {
                    for (Node node:ContentGrid.getChildren())
                    {
                        if (node instanceof Label)
                            ((Label) node).setFont(new Font("Roboto Light", Math.min(ContentGrid.getHeight(), ContentGrid.getWidth()) / currentModel.Size / 2));
                    }
                });
            };
            ContentGrid.widthProperty().addListener(watcher);
            ContentGrid.heightProperty().addListener(watcher);
        });

    }
}
