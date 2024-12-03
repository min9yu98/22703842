import * as types from './mutation_types'

export default {
    [types.MEMBER_ID](state, memberId) {
        state.memberId = memberId
    },
    [types.ERROR_STATE](state, errorState) {
        state.errorState = errorState
    },
    [types.IS_AUTH](state, isAuth) {
        state.isAuth = isAuth
    },
    [types.LOADING_STATUS](state, loadingStatus) { 
        state.loadingStatus = loadingStatus
    }
}
