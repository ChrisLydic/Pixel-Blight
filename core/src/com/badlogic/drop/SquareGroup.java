package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


enum Spread {
    EMPTY,
    SLOW,
    NORMAL,
    FAST,
    BOMB,
    SPRINT
}
enum Clicks {
    SINGLE,
    DOUBLE,
    TRIPLE
}

/**
 * Created by chris on 5/28/2017.
 */
public class SquareGroup {
    private Square[][] squares;
    private ArrayList<Square> newInfectedSquares;
    private float originX;
    private float originY;
    private short count;
    private int totalSquares;
    private int infectedSquares;

    public SquareGroup(int rows, int columns, int[][] board, int[][] corruptedSquares) {
        count = 0;
        infectedSquares = corruptedSquares.length;
        totalSquares = rows * columns;
        newInfectedSquares = new ArrayList<Square>();
        squares = new Square[rows][columns];

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                switch (board[r][c]) {
                    case 0:
                        totalSquares--;
                        squares[r][c] = new Square(Spread.EMPTY, Clicks.SINGLE);
                        break;
                    case 1:
                        squares[r][c] = new Square(Spread.NORMAL, Clicks.SINGLE);
                        break;
                    case 2:
                        squares[r][c] = new Square(Spread.SLOW, Clicks.SINGLE);
                        break;
                    case 3:
                        squares[r][c] = new Square(Spread.FAST, Clicks.SINGLE);
                        break;
                    case 4:
                        squares[r][c] = new Square(Spread.NORMAL, Clicks.DOUBLE);
                        break;
                    case 5:
                        squares[r][c] = new Square(Spread.NORMAL, Clicks.TRIPLE);
                        break;
                    case 6:
                        squares[r][c] = new Square(Spread.BOMB, Clicks.SINGLE);
                        break;
                    case 7:
                        squares[r][c] = new Square(Spread.SPRINT, Clicks.SINGLE);
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (squares[r][c].spreadType == Spread.EMPTY) {
                    continue;
                }

                if (r == 0){
                    if (squares[r + 1][c].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r + 1][c]);
                    }
                } else if (r == squares.length - 1) {
                    if (squares[r - 1][c].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r - 1][c]);
                    }
                } else {
                    if (squares[r + 1][c].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r + 1][c]);
                    }
                    if (squares[r - 1][c].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r - 1][c]);
                    }
                }

                if (c == 0){
                    if (squares[r][c + 1].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r][c + 1]);
                    }
                } else if (c == squares[0].length - 1) {
                    if (squares[r][c - 1].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r][c - 1]);
                    }
                } else {
                    if (squares[r][c + 1].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r][c + 1]);
                    }
                    if (squares[r][c - 1].spreadType != Spread.EMPTY) {
                        squares[r][c].neighbors.add(squares[r][c - 1]);
                    }
                }

                if (squares[r][c].spreadType == Spread.SPRINT) {
                    if (r <= 1){
                        if (squares[r + 2][c].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 2][c]);
                        }
                    } else if (r >= squares.length - 2) {
                        if (squares[r - 2][c].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 2][c]);
                        }
                    } else {
                        if (squares[r + 2][c].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 2][c]);
                        }
                        if (squares[r - 2][c].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 2][c]);
                        }
                    }

                    if (c <= 1){
                        if (squares[r][c + 2].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r][c + 2]);
                        }
                    } else if (c >= squares[0].length - 2) {
                        if (squares[r][c - 2].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r][c - 2]);
                        }
                    } else {
                        if (squares[r][c + 2].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r][c + 2]);
                        }
                        if (squares[r][c - 2].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r][c - 2]);
                        }
                    }
                }

                if (squares[r][c].spreadType == Spread.BOMB) {
                    if (r == 0 && c == 0) {
                        if (squares[r + 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c + 1]);
                        }
                    } else if (r == 0 && c == (squares[0].length - 1)) {
                        if (squares[r + 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c - 1]);
                        }
                    } else if ((r == squares.length - 1) && c == 0) {
                        if (squares[r - 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c + 1]);
                        }
                    } else if ((r == squares.length - 1) && (c == squares[0].length - 1)) {
                        if (squares[r - 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c - 1]);
                        }
                    } else if (r == 0) {
                        if (squares[r + 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c + 1]);
                        }
                        if (squares[r + 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c - 1]);
                        }
                    } else if (r == squares.length - 1) {
                        if (squares[r - 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c + 1]);
                        }
                        if (squares[r - 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c - 1]);
                        }
                    } else if (c == 0) {
                        if (squares[r - 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c + 1]);
                        }
                        if (squares[r + 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c + 1]);
                        }
                    } else if (c == squares[0].length - 1) {
                        if (squares[r - 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c - 1]);
                        }
                        if (squares[r + 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c - 1]);
                        }
                    } else {
                        if (squares[r - 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c + 1]);
                        }
                        if (squares[r - 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r - 1][c - 1]);
                        }
                        if (squares[r + 1][c + 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c + 1]);
                        }
                        if (squares[r + 1][c - 1].spreadType != Spread.EMPTY) {
                            squares[r][c].neighbors.add(squares[r + 1][c - 1]);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < corruptedSquares.length; i++) {
            squares[corruptedSquares[i][0]][corruptedSquares[i][1]].isInfected = true;
        }
    }

    public void setOrigin(float x, float y) {
        originX = x;
        originY = y;

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                squares[r][c].x = originX + ((-10*squares[0].length/2) + c*10);
                squares[r][c].y = originY + ((-10*squares.length/2) + ((squares.length-1)-r)*10);
            }
        }
    }

    public void draw(Batch batch, TextureRegion[][] tiles) {
        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (squares[r][c].spreadType == Spread.EMPTY) {
                    continue;
                }

                if (r == squares.length - 1) {
                    if (squares[r][c].isInfected) {
                        batch.draw(tiles[squares[r][c].color][Square.DARK_EDGE_TILE], squares[r][c].x, squares[r][c].y, Square.SIZE, Square.SIZE);
                    } else {
                        batch.draw(tiles[squares[r][c].color][Square.EDGE_TILE], squares[r][c].x, squares[r][c].y, Square.SIZE, Square.SIZE);
                    }
                } else if (squares[r + 1][c].spreadType == Spread.EMPTY) {
                    if (squares[r][c].isInfected) {
                        batch.draw(tiles[squares[r][c].color][Square.DARK_EDGE_TILE], squares[r][c].x, squares[r][c].y, Square.SIZE, Square.SIZE);
                    } else {
                        batch.draw(tiles[squares[r][c].color][Square.EDGE_TILE], squares[r][c].x, squares[r][c].y, Square.SIZE, Square.SIZE);
                    }
                } else if (squares[r][c].isInfected) {
                    batch.draw(tiles[squares[r][c].color][Square.DARK_TILE], squares[r][c].x, squares[r][c].y, Square.SIZE, Square.SIZE);
                } else {
                    batch.draw(tiles[squares[r][c].color][Square.NORMAL_TILE], squares[r][c].x, squares[r][c].y, Square.SIZE, Square.SIZE);
                }
            }
        }
    }

    public void touched( float x, float y, Enum<ActionType> action ) {
        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (x >= squares[r][c].x && x <= (squares[r][c].x + 10) && y >= squares[r][c].y && y <= (squares[r][c].y + 10) ) {
                    if (squares[r][c].spreadType == Spread.EMPTY) {
                        continue;
                    }

                    if (action == ActionType.TAP) {
                        if (squares[r][c].isInfected) {
                            squares[r][c].clickCount++;

                            if (squares[r][c].clicksType == Clicks.DOUBLE && squares[r][c].clickCount == 2) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.TRIPLE && squares[r][c].clickCount == 3) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.SINGLE && squares[r][c].clickCount == 1) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            }
                        }
                    } else if (action == ActionType.SCATTER) {
                        if (squares[r][c].isInfected && Math.random() > 0.5) {
                            squares[r][c].clickCount++;

                            if (squares[r][c].clicksType == Clicks.DOUBLE && squares[r][c].clickCount == 2) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.TRIPLE && squares[r][c].clickCount == 3) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.SINGLE && squares[r][c].clickCount == 1) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            }
                        }

                        for (Square neighbor : squares[r][c].neighbors) {
                            if (neighbor.isInfected && Math.random() > 0.5) {
                                neighbor.clickCount++;

                                if (neighbor.clicksType == Clicks.DOUBLE && neighbor.clickCount == 2) {
                                    neighbor.isInfected = false;
                                    neighbor.clickCount = 0;
                                } else if (neighbor.clicksType == Clicks.TRIPLE && neighbor.clickCount == 3) {
                                    neighbor.isInfected = false;
                                    neighbor.clickCount = 0;
                                } else if (neighbor.clicksType == Clicks.SINGLE && neighbor.clickCount == 1) {
                                    neighbor.isInfected = false;
                                    neighbor.clickCount = 0;
                                }
                            }

                            for (Square neighborInner : neighbor.neighbors) {
                                if (neighborInner.isInfected && Math.random() > 0.5) {
                                    neighborInner.clickCount++;

                                    if (neighborInner.clicksType == Clicks.DOUBLE && neighborInner.clickCount == 2) {
                                        neighborInner.isInfected = false;
                                        neighborInner.clickCount = 0;
                                    } else if (neighborInner.clicksType == Clicks.TRIPLE && neighborInner.clickCount == 3) {
                                        neighborInner.isInfected = false;
                                        neighborInner.clickCount = 0;
                                    } else if (neighborInner.clicksType == Clicks.SINGLE && neighborInner.clickCount == 1) {
                                        neighborInner.isInfected = false;
                                        neighborInner.clickCount = 0;
                                    }
                                }
                            }
                        }
                    } else if (action == ActionType.BOMB) {
                        if (squares[r][c].isInfected) {
                            squares[r][c].clickCount++;

                            if (squares[r][c].clicksType == Clicks.DOUBLE && squares[r][c].clickCount == 2) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.TRIPLE && squares[r][c].clickCount == 3) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.SINGLE && squares[r][c].clickCount == 1) {
                                squares[r][c].isInfected = false;
                                squares[r][c].clickCount = 0;
                            }
                        }

                        for (Square neighbor : squares[r][c].neighbors) {
                            if (neighbor.isInfected) {
                                neighbor.clickCount++;

                                if (neighbor.clicksType == Clicks.DOUBLE && neighbor.clickCount == 2) {
                                    neighbor.isInfected = false;
                                    neighbor.clickCount = 0;
                                } else if (neighbor.clicksType == Clicks.TRIPLE && neighbor.clickCount == 3) {
                                    neighbor.isInfected = false;
                                    neighbor.clickCount = 0;
                                } else if (neighbor.clicksType == Clicks.SINGLE && neighbor.clickCount == 1) {
                                    neighbor.isInfected = false;
                                    neighbor.clickCount = 0;
                                }
                            }
                        }
                    } else if (action == ActionType.CURE) {
                        if (squares[r][c].isInfected) {
                            squares[r][c].clickCount++;

                            if (squares[r][c].clicksType == Clicks.DOUBLE && squares[r][c].clickCount == 2) {
                                squares[r][c].isInfected = false;
                                squares[r][c].isCured = true;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.TRIPLE && squares[r][c].clickCount == 3) {
                                squares[r][c].isInfected = false;
                                squares[r][c].isCured = true;
                                squares[r][c].clickCount = 0;
                            } else if (squares[r][c].clicksType == Clicks.SINGLE && squares[r][c].clickCount == 1) {
                                squares[r][c].isInfected = false;
                                squares[r][c].isCured = true;
                                squares[r][c].clickCount = 0;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public void corrupt(float delta) {
        newInfectedSquares.clear();
        infectedSquares = 0;

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[0].length; c++) {
                if (squares[r][c].isInfected) {
                    infectedSquares++;

                    if (squares[r][c].spreadType == Spread.NORMAL && !(count == 3 || count == 7)) {continue;}
                    if (squares[r][c].spreadType == Spread.SLOW && count != 7) {continue;}

                    int corruptCount = (int) Math.ceil(squares[r][c].neighbors.size() / 2f);

                    for (int i = 0; i < corruptCount; i++) {
                        Square neighbor = squares[r][c].neighbors.get((int)Math.floor(Math.random() * squares[r][c].neighbors.size()));

                        if (!neighbor.isInfected && !neighbor.isCured && !newInfectedSquares.contains(neighbor)) {
                            newInfectedSquares.add(neighbor);
                        }
                    }
                }
            }
        }

        for (Square newInfected : newInfectedSquares) {
            newInfected.isInfected = true;
        }

        if (count == 7) {
            count = 0;
        } else {
            count++;
        }
    }

    public boolean isWon() {
        return infectedSquares == 0;
    }

    public boolean isLost() {
        return infectedSquares == totalSquares;
    }

    public float getProgress() {
        return 1 - (infectedSquares/(float)totalSquares);
    }

    class Square {
        public int color;
        public Enum<Spread> spreadType;
        public Enum<Clicks> clicksType;
        public boolean isInfected;
        public float x;
        public float y;
        public static final int SIZE = 10;
        public static final int NORMAL_TILE = 0;
        public static final int DARK_TILE = 1;
        public static final int EDGE_TILE = 2;
        public static final int DARK_EDGE_TILE = 3;
        public ArrayList<Square> neighbors;
        public int clickCount;
        public boolean isCured;

        public Square(Enum<Spread> spread, Enum<Clicks> clicks) {
            clickCount = 0;
            spreadType = spread;
            clicksType = clicks;
            isCured = false;

            if (spreadType == Spread.SLOW) {
                color = 2;
            } else if (spreadType == Spread.FAST) {
                color = 5;
            } else if (spreadType == Spread.BOMB) {
                color = 4;
            } else if (spreadType == Spread.SPRINT) {
                color = 6;
            } else if (clicksType == Clicks.DOUBLE) {
                color = 1;
            } else if (clicksType == Clicks.TRIPLE) {
                color = 3;
            } else if (clicksType == Clicks.SINGLE) {
                color = 0;
            }

            neighbors = new ArrayList<Square>();
        }
    }
}
