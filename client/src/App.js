import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { CssBaseline, ThemeProvider } from '@mui/material'

import { ColorModeContext, useMode } from './config/theme'

import Header from './components/global/Header'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Dashboard from './pages/Dashboard'
import Error from './pages/Error'

const App = () => {
    const [theme, colorMode] = useMode()

    return (
        <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
                <CssBaseline>
                    <div className="app">
                        <main className="content">
                            <Header />
                            <Routes>
                                <Route path="/" element={<Login />} />
                                <Route path="/signup" element={<Signup />} />
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
