Eric Anderson
eander29@u.rochester.edu
CSC 242
Project 1
Worked alone

Build instructions:

	- javac *.java

Run instructions:

	- java GameConnect

Output format closely mimics the example output given to us.
Turn in zip only contains src files, submission form, and the README.

Depth level 1 will break the program because that is the surface.

Files:
	- Action.java - interface for an action
	- Action2P.java - interface for an action with two players
	- ActionConnect.java - class for an action for connect four
	- Game.java - interface for a game
	- Game2P.java - class for a game with generic implementation of formal methods
	- GameConnect.java - class for connect four game with full implementation
	- Player.java - class for a player
	- README.txt
	- State.java - interface for a state
	- State2P.java - interface for a state with two players
	- StateConnect.java - class for a state for connect four
	- submission_form.pdf

I think I did a pretty good job of implementing this generically. The interfaces
and class inheritance applies well. I implemented several of the formal methods
generically. While I coded the algorithms non generically, they easily could have
been. But I guess I got a little lazy.

My heuristic function is based off of pieces in the middle column and counts
of contiguous pieces (rows of 3's and rows of 2's).

Tiny board can play random or minimax. Both are computed on the scale of
milliseconds. The minimax player is optimal.

Wider board can play random, minimax, or minimax with pruning. Minimax computes
the first move in ~40 seconds. With pruning it takes on the scale of milliseconds.
It is interesting because for this board if the user is to go first, the cpu
per the algorithm will compute that it is guaranteed to lose if the user plays
optimally. So it will arbitrarily choose a move. In the case of my program,
it will be the left most move. I could've added a selection method but it
was whatever.

Standard board can play random, minimax, minimax with pruning, or minimax with
heuristic and pruning and depth level. Minimax and minimax with pruning would
take an unreasonable amount of time to compute (I waited 10 plus minutes).
Heuristic minimax computes at around ~1 second at depth level 10 and ~1 minute
at depth level 13. It plays pretty well and gives a good challenge with some
interesting moves.


EXAMPLE OUTPUT:

Connect-Four by Eric Anderson
Choose your game:
1. Tiny 3x3x3 Connect-Three
2. Wider 5x3x3 Connect-Three
3. Standard 7x6x4 Connect-Four
Your choice? 3
Choose your opponent:
1. An agent that plays randomly
2. An agent that uses MINIMAX
3. An agent that uses MINIMAX with alpha-beta pruning
4. An agent that uses H-MINIMAX with alpha-beta pruning and a fixed depth cutoff
Your choice? 4
Depth limit? 11
Do you want to play RED (1) or YELLOW (2)?
Your choice? 2

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |
4 |   |   |   |   |   |   |   |
5 |   |   |   |   |   |   |   |
6 |   |   |   |   |   |   |   |

Next to play: Red

I'm thinking...
         visited 1708892 states
         best move: Red @d, value: 41
Elapsed time: 6.479 secs
Red @d

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |
4 |   |   |   |   |   |   |   |
5 |   |   |   |   |   |   |   |
6 |   |   |   | R |   |   |   |

Next to play: Yellow
Your move [column]? d
Elapsed time: 3.145 secs

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |
4 |   |   |   |   |   |   |   |
5 |   |   |   | Y |   |   |   |
6 |   |   |   | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 2006496 states
         best move: Red @b, value: 43
Elapsed time: 6.26 secs
Red @b

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |
4 |   |   |   |   |   |   |   |
5 |   |   |   | Y |   |   |   |
6 |   | R |   | R |   |   |   |

Next to play: Yellow
Your move [column]? c
Elapsed time: 1.42 secs

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |
4 |   |   |   |   |   |   |   |
5 |   |   |   | Y |   |   |   |
6 |   | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 1009778 states
         best move: Red @d, value: 44
Elapsed time: 2.371 secs
Red @d

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |
4 |   |   |   | R |   |   |   |
5 |   |   |   | Y |   |   |   |
6 |   | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? d
Elapsed time: 1.437 secs

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   | Y |   |   |   |
4 |   |   |   | R |   |   |   |
5 |   |   |   | Y |   |   |   |
6 |   | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 958609 states
         best move: Red @c, value: 52
Elapsed time: 3.274 secs
Red @c

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   | Y |   |   |   |
4 |   |   |   | R |   |   |   |
5 |   |   | R | Y |   |   |   |
6 |   | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? c
Elapsed time: 7.889 secs

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 |   |   | R | Y |   |   |   |
6 |   | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 407400 states
         best move: Red @a, value: 54
Elapsed time: 0.922 secs
Red @a

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   |   | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 |   |   | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? c
Elapsed time: 4.995 secs

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   |   |   |   |   |
3 |   |   | Y | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 |   |   | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 660022 states
         best move: Red @d, value: 58
Elapsed time: 2.444 secs
Red @d

    a   b   c   d   e   f   g
1 |   |   |   |   |   |   |   |
2 |   |   |   | R |   |   |   |
3 |   |   | Y | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 |   |   | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? d
Elapsed time: 2.908 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   |   | R |   |   |   |
3 |   |   | Y | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 |   |   | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 389475 states
         best move: Red @a, value: 59
Elapsed time: 0.744 secs
Red @a

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   |   | R |   |   |   |
3 |   |   | Y | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 | R |   | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? b
Elapsed time: 20.455 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   |   | R |   |   |   |
3 |   |   | Y | Y |   |   |   |
4 |   |   | Y | R |   |   |   |
5 | R | Y | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 163269 states
         best move: Red @a, value: 65
Elapsed time: 0.641 secs
Red @a

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   |   | R |   |   |   |
3 |   |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   |   |
5 | R | Y | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? a
Elapsed time: 2.15 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   |   | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   |   |
5 | R | Y | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Red

I'm thinking...
         visited 170702 states
         best move: Red @c, value: 67
Elapsed time: 0.966 secs
Red @c

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   |   |
5 | R | Y | R | Y |   |   |   |
6 | R | R | Y | R |   |   |   |

Next to play: Yellow
Your move [column]? g
Elapsed time: 22.843 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   |   |
5 | R | Y | R | Y |   |   |   |
6 | R | R | Y | R |   |   | Y |

Next to play: Red

I'm thinking...
         visited 238761 states
         best move: Red @g, value: 68
Elapsed time: 0.653 secs
Red @g

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   |   |
5 | R | Y | R | Y |   |   | R |
6 | R | R | Y | R |   |   | Y |

Next to play: Yellow
Your move [column]? g
Elapsed time: 6.242 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   | Y |
5 | R | Y | R | Y |   |   | R |
6 | R | R | Y | R |   |   | Y |

Next to play: Red

I'm thinking...
         visited 151484 states
         best move: Red @e, value: 75
Elapsed time: 0.318 secs
Red @e

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   | Y |
5 | R | Y | R | Y |   |   | R |
6 | R | R | Y | R | R |   | Y |

Next to play: Yellow
Your move [column]? e
Elapsed time: 8.842 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   | Y |
5 | R | Y | R | Y | Y |   | R |
6 | R | R | Y | R | R |   | Y |

Next to play: Red

I'm thinking...
         visited 83847 states
         best move: Red @f, value: 77
Elapsed time: 0.205 secs
Red @f

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   | Y |
5 | R | Y | R | Y | Y |   | R |
6 | R | R | Y | R | R | R | Y |

Next to play: Yellow
Your move [column]? f
Elapsed time: 8.716 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R |   |   | Y |
5 | R | Y | R | Y | Y | Y | R |
6 | R | R | Y | R | R | R | Y |

Next to play: Red

I'm thinking...
         visited 29919 states
         best move: Red @e, value: 77
Elapsed time: 0.09 secs
Red @e

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y |   |   |   |
4 | R |   | Y | R | R |   | Y |
5 | R | Y | R | Y | Y | Y | R |
6 | R | R | Y | R | R | R | Y |

Next to play: Yellow
Your move [column]? e
Elapsed time: 3.773 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 |   |   | R | R |   |   |   |
3 | Y |   | Y | Y | Y |   |   |
4 | R |   | Y | R | R |   | Y |
5 | R | Y | R | Y | Y | Y | R |
6 | R | R | Y | R | R | R | Y |

Next to play: Red

I'm thinking...
         visited 7324 states
         best move: Red @a, value: -1000
Elapsed time: 0.035 secs
Red @a

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 | R |   | R | R |   |   |   |
3 | Y |   | Y | Y | Y |   |   |
4 | R |   | Y | R | R |   | Y |
5 | R | Y | R | Y | Y | Y | R |
6 | R | R | Y | R | R | R | Y |

Next to play: Yellow
Your move [column]? e
Elapsed time: 14.583 secs

    a   b   c   d   e   f   g
1 |   |   |   | Y |   |   |   |
2 | R |   | R | R | Y |   |   |
3 | Y |   | Y | Y | Y |   |   |
4 | R |   | Y | R | R |   | Y |
5 | R | Y | R | Y | Y | Y | R |
6 | R | R | Y | R | R | R | Y |

Winner: Yellow
Total time:
        Red: 25.401999999999997 secs
        Yellow: 109.39799999999998 secs