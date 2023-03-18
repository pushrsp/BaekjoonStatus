import React, { useContext } from 'react'
import { Box, IconButton, useTheme, InputBase } from '@mui/material'
import LightModeOutlinedIcons from '@mui/icons-material/LightModeOutlined'
import DarkModeOutlinedIcons from '@mui/icons-material/DarkModeOutlined'
import NotificationsOutlinedIcon from '@mui/icons-material/NotificationsOutlined'
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined'
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined'
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined'

import { ColorModeContext, tokens } from '../../config/theme'

const Header = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const colorMode = useContext(ColorModeContext)

    return (
        <Box display="flex" justifyContent="space-between" p={2}>
            <Box display="flex" backgroundColor={colors.primary[400]} borderRadius="3px">
                <InputBase sx={{ ml: 2, flex: 1 }} placeholder="Search" />
                <IconButton type="button" sx={{ p: 1 }}>
                    <SearchOutlinedIcon />
                </IconButton>
            </Box>

            <Box display="flex">
                <IconButton onClick={colorMode.toggleColorMode}>
                    {theme.palette.mode === 'dark' ? (
                        <DarkModeOutlinedIcons />
                    ) : (
                        <LightModeOutlinedIcons />
                    )}
                </IconButton>
                <IconButton>
                    <NotificationsOutlinedIcon />
                </IconButton>
                <IconButton>
                    <SettingsOutlinedIcon />
                </IconButton>
                <IconButton>
                    <PersonOutlinedIcon />
                </IconButton>
            </Box>
        </Box>
    )
}

export default Header
