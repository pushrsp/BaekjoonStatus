import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { RecoilRoot } from 'recoil'
import { ProSidebarProvider } from 'react-pro-sidebar'

import './index.css'
import App from './App'

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <RecoilRoot>
                <ProSidebarProvider>
                    <App />
                </ProSidebarProvider>
            </RecoilRoot>
        </BrowserRouter>
    </React.StrictMode>,
)
