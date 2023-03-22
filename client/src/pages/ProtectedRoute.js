import React from 'react'
import { useRecoilValue } from 'recoil'

import { userState } from '../atom'

import Login from './Login'

const ProtectedRoute = ({ children }) => {
    const user = useRecoilValue(userState)

    if (Object.keys(user).length === 0) return <Login />

    return children
}

export default ProtectedRoute
