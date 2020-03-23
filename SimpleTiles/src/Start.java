import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Start extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int[][] tileMap = { {0,0,0,0,0,0,0,0,0,0},
                                       {0,0,0,0,0,0,0,0,0,0},
                                       {0,0,0,0,0,0,0,0,0,0},
                                       {0,0,0,0,0,0,0,0,0,0},
                                       {0,0,0,0,0,0,0,0,0,0},
                                       {0,0,0,0,0,0,0,0,0,0} };
    private static int[] gameSize = {1120,640}; //{70,40} tile map
    private static int[] mapSize = {70,40};
    private static int tileSize = 16;
    private static PerlinNoise perlin;
    private static List<Integer> perlinN;
    private static List<Integer> perlinNBack_1;
    private static List<Integer> perlinNBack_2;

    public Start() {
        GamePanel game = new GamePanel();
        this.setBounds(0, 0, 0, 0);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(game);
        //this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    public class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        File dirBackground = new File("assets/background.png");
        File dirTile_1 = new File("assets/block_1a.png");
        File dirBack_1 = new File("assets/back_1.png");
        File dirBack_2 = new File("assets/back_2.png");
        BufferedImage imgBackground, imgTile_1, imgBack_1, imgBack_2;
        int[] imgDims;

        public GamePanel() {
            this.setFocusable(true);
            try {
                imgBackground = ImageIO.read(dirBackground);
                imgTile_1 = ImageIO.read(dirTile_1);
                imgBack_1 = ImageIO.read(dirBack_1);
                imgBack_2 = ImageIO.read(dirBack_2);
                imgDims = new int[] {imgBackground.getWidth(),imgBackground.getHeight()};
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            perlin = new PerlinNoise();
            perlinN       = perlin.combineNoise(perlin.generateNoise(128, 128, 5, 1, gameSize[0]));
            perlinNBack_1 = perlin.combineNoise(perlin.generateNoise(128, 128, 5, 1, gameSize[0]));
            perlinNBack_2 = perlin.combineNoise(perlin.generateNoise(128, 128, 5, 1, gameSize[0]));
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(gameSize[0],gameSize[1]);
        }
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g2d);

            g2d.drawImage(imgBackground,0,0,null);
            /*
            for (int i=0; i<gameSize[0]; i=i+tileSize) {
                for (int j=gameSize[1]; j>(perlinNBack_1.get(i)+(gameSize[1]/2)); j=j-16) {
                    g2d.drawImage(imgBack_1, i, j, null);
                }
            }
            for (int i=0; i<gameSize[0]; i=i+tileSize) {
                for (int j=gameSize[1]; j>(perlinNBack_2.get(i)+(gameSize[1]/2)); j=j-16) {
                    g2d.drawImage(imgBack_2, i, j, null);
                }
            }
            */
            for (int i=0; i<gameSize[0]; i=i+tileSize) {
                for (int j=gameSize[1]; j>(perlinN.get(i)+(gameSize[1]/2)); j=j-16) {
                    g2d.drawImage(imgTile_1, i, j, null);
                }
            }
            for (int i=0; i<perlinN.size()-1; i++) {
                g2d.drawLine(i, (gameSize[1] / 2) + perlinN.get(i), i+1, (gameSize[1] / 2) + perlinN.get(i+1));
            }
        }
    }
    public static void main(String[] args) {
        Start start = new Start();
    }
}