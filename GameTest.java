import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {


    Game game;


    @BeforeEach
    void before() {
        game = new Game();


    }

    @Test
    void testPlayGame() {
        game.playGame();
//        game.playGameQueue();
//        game.printEndBoards();
    }

    @Test
    void testPrintNode() {
        System.out.println("working");

        game.root.printGameBoard();



    }

    @Test
    void testCode() {
        assertEquals(Math.pow(2,16),game.goal.getCode());

    }

    @Test
    void testIndexToGrid() {
        assertEquals(3,game.indexToGrid(1));
        assertEquals(10, game.indexToGrid(4));
        assertEquals(24, game.indexToGrid(16));
        assertEquals(38, game.indexToGrid(28));
        assertEquals(45,game.indexToGrid(31));
        assertEquals(32,game.indexToGrid(24));
    }

    @Test
    void testGridToIndex() {
        assertEquals(1, game.gridToIndex(3));
        assertEquals(4,game.gridToIndex(10));
        assertEquals(16,game.gridToIndex(24));
        assertEquals(28, game.gridToIndex(38));
        assertEquals(31,game.gridToIndex(45));
        assertEquals(-1, game.gridToIndex(8));
        assertEquals(-1, game.gridToIndex(12));
        assertEquals(-1, game.gridToIndex(35));
        assertEquals(-1,game.gridToIndex(47));

    }

    @Test
    void testGenerateJumps() {
        ArrayList<Integer> arr = game.generateJumps(9);
        for(Integer jump : arr)
            System.out.println(jump);

        assertTrue(arr.contains(1));
        assertTrue(arr.contains(23));
        assertTrue(arr.contains(7));
        assertTrue(arr.contains(11));

        arr = game.generateJumps(24);
        System.out.println(32 % 7);
        assertTrue(arr.contains(26));
        assertTrue(arr.contains(32));

        arr = game.generateJumps(31);
        assertTrue(arr.contains(23));
        assertEquals(1,arr.size());

        arr= game.generateJumps(13);
        assertTrue(arr.contains(15));
        assertEquals(1,arr.size());
    }

    @Test
    void testSanitize() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(0);
        arr.add(1);
        arr.add(33);
        arr.add(-1);
        arr.add(300);
        arr = game.sanitize(arr);
        assertEquals(0,arr.size());

    }

    @Test
    void testInitializeJumps() {
        game.initializeJumps();
        System.out.println(game.jumps);
    }

    @Test
    void testFindNewEmp() {
        assertEquals(25,game.findNewEmp(26,24));
        assertEquals(4,game.findNewEmp(9,1));
        assertEquals(22,game.findNewEmp(21,23));
        assertEquals(29,game.findNewEmp(32,24));
        assertEquals(25,game.findNewEmp(26,24));
    }

    @Test
    void testMakeAdjNode() {
        Node adjNode = game.makeAdjNode(game.root.getGameBoard(), 16,18);
        System.out.println(adjNode.getGameBoard() == game.root.getGameBoard());
        byte[] newBoard = new byte[game.boardSize];

        for (int i = 0; i < game.boardSize; i++) {
            newBoard[i] = game.root.getGameBoard()[i];
        }
        newBoard[17] = 0;
        newBoard[16] = 1;
        newBoard[18] = 0;

        for(int i = 0; i < game.boardSize; i ++) {
            assertEquals(newBoard[i],adjNode.getGameBoard()[i]);
        }


    }

    @Test
    void testExploreAdjacentNode() {
        byte[] arr = new byte[game.boardSize];
        for(int i = 0; i < game.boardSize; i++) {
            arr[i] = 1;
        }
        arr[16] = 0;
        arr[15] = 0;
        arr[18] = 0;
        Node n = new Node(arr);
        System.out.println("Node n");
        n.printGameBoard();
        System.out.println("exploring");
        game.exploreAdjacentNodes(n.getCode());


        game.exploreAdjacentNodes(game.goal.getCode());
        System.out.println("!!!!!!!!!!!!");
        game.exploreAdjacentNodes(game.root.getCode());
    }





}
