import { createStore } from "vuex";
import getters from './getters'
import mutations from './mutations'
import actions from './actions'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
    state: {
        member: null,
        isLogin: false,
        memberId: null
    },
    mutations,
    getters,
    actions,
    plugins: [
        createPersistedState({
            key: 'vuex', // 저장될 key 이름
            storage: window.localStorage, // 저장 방식: localStorage
            paths: ['isLogin', 'memberId', 'member'] // 저장할 state 값들
        })
    ]
})