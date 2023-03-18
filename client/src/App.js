import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { CssBaseline, ThemeProvider } from '@mui/material'

import { ColorModeContext, useMode } from './config/theme'

import Header from './components/global/Header'
import Sidebar from './components/global/SideBar'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import MyCard from './pages/MyCard'
import Error from './pages/Error'

const App = () => {
    const [theme, colorMode] = useMode()

    return (
        <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
                <CssBaseline>
                    <div className="app">
                        <Sidebar />
                        <main className="content">
                            <Header />
                            <Routes>
                                <Route path="/" element={<Dashboard />} />
                                {/*<Route path="/card" element={<MyCard />} />*/}
                                {/*<Route path="/login" element={<Login />} />*/}
                                {/*<Route path="/*" element={<Error />} />*/}
                            </Routes>
                        </main>
                    </div>
                </CssBaseline>
            </ThemeProvider>
        </ColorModeContext.Provider>
    )
}

export default App
