export default {
    getMemberId: state => state.memberId,
    getErrorState: state => state.errorState,
    getIsAuth: state => state.isAuth,
    loggedIn(state) {
        return !!state.member
    }
}