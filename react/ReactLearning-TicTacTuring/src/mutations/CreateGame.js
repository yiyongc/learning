import Relay from 'react-relay/classic'

export default class CreateGame extends Relay.Mutation {

	getVariables() {
		return {
      p1playerId: this.props.p1playerId,
      winnerId: this.props.winnerId,
      p1Guess: this.props.guess,
      p1GuessCorrect: this.props.guessCorrect
		}
	}

	getMutation () {
		return Relay.QL`mutation{createGame}`
	}

	getFatQuery () {
		return Relay.QL`
			fragment on CreateGamePayload {
        p1player
			}
		`
	}

	getConfigs() {
		return [
			{
				type: 'RANGE_ADD',
				parentName: 'p1player',
        parentID: this.props.p1playerId,
				connectionName: 'p1games',
				edgeName: 'edge',
				rangeBehaviors: {
					'': 'append',
				},
			}
		]
	}

}
