import React, { useEffect } from 'react'
import { Routes, Route } from 'react-router-dom'
import { CssBaseline, ThemeProvider } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import { useRecoilState, useSetRecoilState } from 'recoil'

import { ColorModeContext, useMode } from './config/theme'
import { userState } from './atom'

import Header from './components/global/Header'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Dashboard from './pages/Dashboard'
import Error from './pages/Error'
import ProtectedRoute from './pages/ProtectedRoute'

const App = () => {
    const [theme, colorMode] = useMode()
    const navigate = useNavigate()
    const setUser = useSetRecoilState(userState)

    useEffect(() => {
        const token = window.localStorage.getItem('token')
        console.log(token)
        ;(async () => {
            // if (token === null) return
            // const { data } = await axios.get(`${process.env.REACT_APP_SERVER_URL}/auth/me`, {
            //     headers: {
            //         Authorization: `Bearer ${token}`,
            //     },
            //     Authorization: token,
            // })
            //
            // console.log(data)
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
