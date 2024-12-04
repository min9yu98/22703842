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
    setIsLogin(state, isLogin) {
        state.isLogin = isLogin;
    },
    setMemberId(state, memberId) {
        state.memberId = memberId;
    },
    setMember(state, member) {
        state.member = member;
    },
    // 로그아웃
    logout(state) {
        state.isLogin = false;
        state.memberId = null;
        state.member = null;
    }
}
