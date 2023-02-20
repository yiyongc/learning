import React from 'react';
import Relay from 'react-relay/classic';
import { Stage } from 'react-konva';
import { Board, Squares } from '../styled/TicTacToe';
import TuringTest from '../styled/TuringTest';
import CreateGame from '../mutations/CreateGame';

class TicTacToe extends React.Component {

  constructor(props) {
    super(props);
    // All winning combinations
    this.combos = [
      [0,1,2],
      [3,4,5],
      [6,7,8],
      [0,3,6],
      [1,4,7],
      [2,5,8],
      [0,4,8],
      [2,4,6]
    ]
  }

  state = {
    rows: 3,
    gameState: new Array(9).fill(false),
    ownMark: 'X',
    otherMark: 'O',
    gameOver: false,
    yourTurn: true,
    winner: false,
    win: false
  }

  componentWillMount() {
    // For board lines
    const height = window.innerHeight;
    const width = window.innerWidth;
    const stageSize = Math.min(height, width) * 0.8;
    const rows = this.state.rows;
    const unit = stageSize / rows;

    // For each square component
    const coordinates = [];
    for (let y = 0; y < rows; y++) {
      for (let x = 0; x < rows; x++) {
        coordinates.push([x*unit, y*unit]);
      }
    }

    this.setState({
      stageSize,
      rows,
      unit,
      coordinates
    })
  }

  move = (marker, index) => {
    console.log('Move made', marker, index);
    this.setState((prevState, props) => {
      let { gameState, yourTurn, gameOver, winner } = prevState;
      yourTurn = !yourTurn;
      gameState.splice(index, 1, marker); // Replace specific index with marker
      const foundWin = this.winChecker(gameState);
      if (foundWin) {
        winner = gameState[foundWin[0]];
      }
      // Check if game is over (no blank squares or winner found)
      if (foundWin || !gameState.includes(false)) {
        gameOver = true;
      }
      if (!yourTurn && !gameOver) {
        this.makeAiMove(gameState);
      }
      return {
        gameState,
        yourTurn,
        gameOver,
        win: foundWin || false,
        winner
      }
    })
  }

  makeAiMove = (gameState) => {
    const otherMark = this.state.otherMark;
    const openSquares = [];
    gameState.forEach((square, index) => {
      if (!square) {
        openSquares.push(index);
      }
    });
    const aiMove = openSquares[this.random(0, openSquares.length)];

    const aiDelay = this.random(1, 3) * 1000;

    setTimeout(() => {
      this.move(otherMark, aiMove);
    }, aiDelay)
  }

  random = (min, max) => {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max-min)) + min;
  }

  winChecker = (gameState) => {
    const combos = this.combos;
    return combos.find((combo) => {
      const [a, b, c] = combo;
      return (gameState[a] !== false && gameState[a] === gameState[b] && gameState[b] === gameState[c]);
    })
  }

  turingTest = () => {
    const gameOver = this.state.gameOver;
    if (gameOver) {
      return (
        <TuringTest recordGame={this.recordGame} />
      )
    }
  }

  recordGame = (guess) => {
    const { user } = this.props.viewer;
    const { relay } = this.props;
    let { winner, ownMark } = this.state;
    if (user) {
      const winnerId = (winner === ownMark) ? user.id : undefined;
      const guessCorrect = (guess === 'ROBOT') ? true : false; // TODO add logic
      relay.commitUpdate(
        new CreateGame({
          p1playerId: user.id,
          winnerId,
          guess,
          guessCorrect
        })
      )
    }
    this.setState({
      gameState: new Array(9).fill(false),
      gameOver: false,
      yourTurn: true,
      winner: false,
      win: false
    })
  }

  render() {
    const { stageSize, rows, unit, coordinates, gameState, win, gameOver, yourTurn, ownMark } = this.state;
    return (
      <div>
        <Stage width={stageSize} height={stageSize}>
          <Board stageSize={stageSize} rows={rows} unit={unit} />
          <Squares unit={unit}
                   coordinates={coordinates}
                   gameState={gameState}
                   gameOver={gameOver}
                   move={this.move}
                   win={win}
                   yourTurn={yourTurn}
                   ownMark={ownMark}/>
        </Stage>
        {this.turingTest()}
      </div>
    )
  }
}

export default Relay.createContainer(
  TicTacToe, {
    fragments: {
      viewer: () => Relay.QL`
        fragment on Viewer {
          user {
            id
          }
        }
      `
    }
  }
)
