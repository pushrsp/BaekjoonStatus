import React from 'react'
import { useRecoilValue } from 'recoil'
import { useTheme } from '@mui/material'
import Loading from 'react-fullscreen-loading'

import { userState, loadingState } from '../atom'
import { tokens } from '../config/theme'

import Login from './Login'

const ProtectedRoute = ({ children }) => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const user = useRecoilValue(userState)
    const loading = useRecoilValue(loadingState)

    if (loading)
        return (
            <Loading
                loading
                background={colors.primary['400']}
                loaderColor={colors.greenAccent['300']}
            />
        )

    if (Object.keys(user).length === 0) return <Login />

    return children
}

export default ProtectedRoute
