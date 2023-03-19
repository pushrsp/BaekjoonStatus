import React, { useContext } from 'react'
import { Box, IconButton, useTheme, InputBase } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined'
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined'
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined'
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined'

import { ColorModeContext, tokens } from '../../config/theme'

const Header = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const colorMode = useContext(ColorModeContext)
    const navigate = useNavigate()

    return (
        <Box display="flex" justifyContent="space-between" p={2}>
            <Box display="flex" backgroundColor={colors.primary[400]} borderRadius="3px">
                {/*<InputBase sx={{ ml: 2, flex: 1 }} placeholder="Search" />*/}
                {/*<IconButton type="button" sx={{ p: 1 }}>*/}
                {/*    <SearchOutlinedIcon />*/}
                {/*</IconButton>*/}
            </Box>

            <Box display="flex">
                <IconButton onClick={colorMode.toggleColorMode}>
                    {theme.palette.mode === 'dark' ? (
                        <DarkModeOutlinedIcon />
                    ) : (
                        <LightModeOutlinedIcon />
                    )}
                </IconButton>
                {/*<IconButton onClick={() => navigate('/login', { replace: true })}>*/}
                {/*    <LoginOutlinedIcon />*/}
                {/*</IconButton>*/}
                {/*<IconButton>*/}
                {/*    <SettingsOutlinedIcon />*/}
                {/*</IconButton>*/}
                {/*<IconButton>*/}
                {/*    <PersonOutlinedIcon />*/}
                {/*</IconButton>*/}
            </Box>
        </Box>
    )
}

export default Header
