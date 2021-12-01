import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

class MazeMechanics {
    private static int[][] maze = { { -4, 2 }, { -1, -3 }, { 3, -1 },
            { -3, 4 }, { -1, -4 }, { 3, -2 },
            { -3, 0 }, { 1, 1 }, { 3, -3 },
            { -3, -1 }, { 1, 2 }, { 3, -4 },
            { -3, -3 }, { 1, 4 },
            { -3, -4 }, { 1, -1 },
            { -2, 2 }, { 1, -2 },
            { -1, 0 }, { 1, -3 },
            { -1, 2 }, { 3, 1 },
            { -1, 3 }, { 3, 2 },
            { -1, -1 }, { 3, 3 } };

    private static int[] curr_pos = { 4, -4 };
    private static int[] end = { -4, 4 };

    static void resetPlayer() {
        MazeMechanics.curr_pos[0] = 4;
        MazeMechanics.curr_pos[1] = -4;
        System.out.println("Player reset");
    }

    static int get_block(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (x > 4 || x < -4 || y > 4 || y < -4) {
            return 0;
        }
        for (int i = maze.length - 1; i >= 0; i--) {
            if (maze[i][0] == x && maze[i][1] == y) {
                return 2;
            }
        }
        if (x == end[0] && y == end[1]) {
            return 3;
        }
        return 1;
    }

    static boolean moveUp() {
        curr_pos[1] += 1;
        if (get_block(curr_pos) == 0 || get_block(curr_pos) == 2) {
            curr_pos[1] -= 1;
            return false;
        }
        return true;
    }

    static boolean moveDown() {
        curr_pos[1] -= 1;
        if (get_block(curr_pos) == 0 || get_block(curr_pos) == 2) {
            curr_pos[1] += 1;
            return false;
        }
        return true;
    }

    static boolean moveLeft() {
        curr_pos[0] -= 1;
        if (get_block(curr_pos) == 0 || get_block(curr_pos) == 2) {
            curr_pos[0] += 1;
            return false;
        }
        return true;
    }

    static boolean moveRight() {
        curr_pos[0] += 1;
        if (get_block(curr_pos) == 0 || get_block(curr_pos) == 2) {
            curr_pos[0] -= 1;
            return false;
        }
        return true;
    }

    static boolean isEnd() {
        if (curr_pos[0] == end[0] && curr_pos[1] == end[1]) {
            return true;
        }
        return false;
    }

    static int[][] renderImages() {
        int[][] renderarray = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 } };
        int x = 0, y = 0;
        for (int i = curr_pos[0] - 2; i <= curr_pos[0] + 2; i++) {
            for (int j = curr_pos[1] + 2; j >= curr_pos[1] - 2; j--) {
                renderarray[x][y] = get_block(new int[] { i, j });
                x++;
            }
            y++;
            x = 0;
        }
        return renderarray;
    }
}

public class project extends Application {
    Scene scene1, scene2, scene3, scene4;

    @Override
    public void start(Stage stage) {
        ImageView viewgrid[][] = new ImageView[5][5];
        int rendergraph[][] = MazeMechanics.renderImages();
        Image img0 = new Image("file:img0.png");
        Image img1 = new Image("file:img1.png");
        Image img2 = new Image("file:img2.png");
        Image playerRight = new Image("file:player_right.png");
        Image playerLeft = new Image("file:player_left.png");
        Image end = new Image("file:img4.jpg");

        // START PAGE
        Label titleLabel = new Label("MAZE RUNNER");
        Font fnt = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
        titleLabel.setFont(fnt);
        Button button1 = new Button("Start");
        button1.setFont(fnt);
        button1.setOnAction(e -> {
            MazeMechanics.resetPlayer();
            stage.setScene(scene2);
            int rendergraph2[][] = MazeMechanics.renderImages();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (rendergraph2[i][j] == 1) {
                        viewgrid[i][j].setImage(img1);
                    } else if (rendergraph2[i][j] == 2) {
                        viewgrid[i][j].setImage(img2);
                    } else if (rendergraph2[i][j] == 0) {
                        viewgrid[i][j].setImage(img0);
                    } else if (rendergraph2[i][j] == 3) {
                        viewgrid[i][j].setImage(end);

                    }
                }
            }
            viewgrid[2][2].setImage(playerRight);
        });
        Button button2 = new Button("Rules");
        button2.setOnAction(e -> stage.setScene(scene4));
        button2.setFont(fnt);
        Button button3 = new Button("Quit");
        button3.setFont(fnt);
        button3.setOnAction(e -> {
            System.exit(0);
        });
        // Pane pane = new Pane();
        VBox vbox = new VBox(titleLabel, button1, button2, button3);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(50);
        scene1 = new Scene(vbox, 500, 500);
        stage.setScene(scene1);
        stage.setTitle("Maze Game");
        stage.show();

        // PAGE 2
        GridPane gridpane = new GridPane();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView imgview = new ImageView();
                imgview.setFitWidth(100);
                imgview.setFitHeight(100);
                imgview.setImage(img0);
                viewgrid[j][i] = imgview;
                gridpane.setConstraints(imgview, i, j);
                gridpane.getChildren().addAll(imgview);
            }
        }
        rendergraph = MazeMechanics.renderImages();
        System.out.println(Arrays.deepToString(rendergraph));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (rendergraph[i][j] == 1) {
                    viewgrid[i][j].setImage(img1);
                } else if (rendergraph[i][j] == 2) {
                    viewgrid[i][j].setImage(img2);
                } else if (rendergraph[i][j] == 0) {
                    viewgrid[i][j].setImage(img0);
                } else if (rendergraph[i][j] == 3) {
                    viewgrid[i][j].setImage(end);

                }
            }
        }
        viewgrid[2][2].setImage(playerRight);

        Pane pane = new Pane();
        TextField tf = new TextField();
        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                KeyCode code = ke.getCode();
                switch (code) {
                    case UP:
                        System.out.println("UP");
                        MazeMechanics.moveUp();
                        break;
                    case DOWN:
                        System.out.println("DOWN");
                        MazeMechanics.moveDown();
                        break;
                    case LEFT:
                        System.out.println("LEFT");
                        MazeMechanics.moveLeft();
                        viewgrid[2][2].setImage(playerLeft);
                        break;
                    case RIGHT:
                        System.out.println("RIGHT");
                        MazeMechanics.moveRight();
                        viewgrid[2][2].setImage(playerRight);
                        break;
                    default:
                        System.out.println(code);
                }
                if (MazeMechanics.isEnd()) {
                    System.out.println("You won!");
                }
                int rendergraph[][] = MazeMechanics.renderImages();
                for (int i = 0; i < 5; i++) {
                    for (int j = 4; j >= 0; j--) {
                        if (i == 2 && j == 2)
                            continue;
                        if (rendergraph[i][j] == 1) {
                            viewgrid[i][j].setImage(img1);
                        } else if (rendergraph[i][j] == 2) {
                            viewgrid[i][j].setImage(img2);
                        } else if (rendergraph[i][j] == 0) {
                            viewgrid[i][j].setImage(img0);
                        } else if (rendergraph[i][j] == 3) {
                            viewgrid[i][j].setImage(end);
                        }
                    }
                }
                if (MazeMechanics.isEnd()) {
                    System.out.println("helo!");
                    stage.setScene(scene3);
                }
            }
        });
        pane.getChildren().addAll(tf, gridpane);

        scene2 = new Scene(pane, 500, 500);

        // PAGE 3
        Label titleLabel_l = new Label("YOU WON!!!!");
        titleLabel_l.setFont(fnt);
        Button button_l1 = new Button("Go back to Start");
        button_l1.setFont(fnt);
        button_l1.setOnAction(e -> stage.setScene(scene1));
        Button button_l3 = new Button("Quit");
        button_l3.setFont(fnt);
        button_l3.setOnAction(e -> System.exit(0));
        // Pane pane = new Pane();
        VBox vbox_l = new VBox(titleLabel_l, button_l1, button_l3);
        vbox_l.setAlignment(Pos.CENTER);
        vbox_l.setSpacing(50);
        scene3 = new Scene(vbox_l, 500, 500);

        // PAGE 4 - RULES
        Label rLabel = new Label("RULES");
        rLabel.setFont(fnt);
        String[] rulesArray = new String[] {
                "The objective is to find the gold block!",
                "You can move using the arrow keys",
                "You can only move on grass, not on void or stone blocks"
        };
        VBox rulesVBox = new VBox();
        rulesVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < rulesArray.length; i++) {
            Label rule = new Label(rulesArray[i]);
            rulesVBox.getChildren().addAll(rule);
        }
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> stage.setScene(scene1));
        VBox rulesPage = new VBox(20, rLabel, rulesVBox, backBtn);
        rulesPage.setAlignment(Pos.CENTER);

        scene4 = new Scene(rulesPage, 500, 500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
