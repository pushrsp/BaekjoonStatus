import React, { useEffect } from 'react'
import { Routes, Route } from 'react-router-dom'
import { CssBaseline, ThemeProvider } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import { useSetRecoilState } from 'recoil'

import { ColorModeContext, useMode } from './config/theme'
import { userState } from './atom'

import Header from './components/global/Header'
import Signup from './pages/Signup'
import Dashboard from './pages/Dashboard'
import Error from './pages/Error'
import ProtectedRoute from './pages/ProtectedRoute'

const App = () => {
    const [theme, colorMode] = useMode()
    const setUser = useSetRecoilState(userState)

    useEffect(() => {
        ;(async () => {
            const token = window.localStorage.getItem('@token')
            if (token !== null) {
                const {
                    data: { code, data },
                } = await axios.get(`${process.env.REACT_APP_SERVER_URL}/auth/me`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                })

                if (code === '0000') {
                    setUser({ id: data.id, username: data.username })
                }
            }
        })()
    }, [])

    return (
        <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
                <CssBaseline>
                    <div className="app">
                        <main className="content">
                            <Header />
                            <Routes>
                                <Route
                                    path="/"
                                    element={
                                        <ProtectedRoute>
                                            <Dashboard />
                                        </ProtectedRoute>
                                    }
                                />
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
