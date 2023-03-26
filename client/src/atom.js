import { atom } from 'recoil'

const userState = atom({
    key: 'userState',
    default: {},
})

const loadingState = atom({
    key: 'loadingState',
    default: true,
})

export { userState, loadingState }
