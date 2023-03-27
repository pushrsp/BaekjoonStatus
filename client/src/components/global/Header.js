import React, { useContext } from 'react'
import { Box, IconButton, useTheme } from '@mui/material'
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined'
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined'
import LogoutOutlined from '@mui/icons-material/LogoutOutlined'
import { useRecoilState } from 'recoil'

import { ColorModeContext, tokens } from '../../config/theme'
import { userState } from '../../atom'

const Header = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const colorMode = useContext(ColorModeContext)
    const [user, setUser] = useRecoilState(userState)

    const onLogout = () => {
        window.localStorage.removeItem('@token')
        setUser({})
    }

    return (
        <Box display="flex" justifyContent="space-between" p={2}>
            <Box display="flex" backgroundColor={colors.primary[400]} borderRadius="3px"></Box>

            <Box display="flex">
                <IconButton onClick={colorMode.toggleColorMode}>
                    {theme.palette.mode === 'dark' ? (
                        <DarkModeOutlinedIcon />
                    ) : (
                        <LightModeOutlinedIcon />
                    )}
                </IconButton>
                {Object.keys(user).length > 0 && (
                    <IconButton onClick={onLogout}>
                        <LogoutOutlined />
                    </IconButton>
                )}
            </Box>
        </Box>
    )
}

export default Header
