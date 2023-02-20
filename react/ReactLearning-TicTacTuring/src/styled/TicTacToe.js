import React from 'react';
import { Layer, Line, Text } from 'react-konva';

export const Board = ({ unit, stageSize, rows }) => {
  const grid = [];
  const stroke = 'grey'; // Color of the Line
  const strokeWidth = 10; // Thickness of the Line

  for (let i = 1; i < rows; i++) {
    let position = unit * i;
    grid.push(
      <Line key={i+'v'} points={[position, 0, position, stageSize]} stroke={stroke} strokeWidth={strokeWidth} />
    )
    grid.push(
      <Line key={i+'h'} points={[0, position, stageSize, position]} stroke={stroke} strokeWidth={strokeWidth} />
    )
  }

  return (
    <Layer>
      { grid }
    </Layer>
  )
}

export const Squares = ({ move, unit, coordinates, gameState, win, gameOver, yourTurn, ownMark }) => {
  const squares = coordinates.map((position, index) => {
    let makeMove = move;
    const mark = gameState[index];
    let fill = 'black'   // Standard color for symbol
    if (win && win.includes(index)) {
      fill = 'lightgreen' // Change symbol color when game is won
    }
    if (gameOver || !yourTurn || mark) {
      makeMove = () => console.log('Not allowed to make a move!');
    }
    return <Text key={index} index={index} x={position[0]} y={position[1]} align={'center'}
                 fill={fill} text={mark} fontSize={unit} fontFamily={'Helvetica'} width={unit}
                 onClick={(event) => {
                   const index = event.target.index;
                   makeMove(ownMark, index);
                 }}/>
  });

  return (
    <Layer>
      { squares }
    </Layer>
  )
}
