import React from 'react'
import { Box } from '@mui/material'

import BreadCrumb from '../components/common/BreadCrumb'

const Dashboard = () => {
    return (
        <Box m="20px">
            <Box display="flex" justifyContent="space-between" alignItems="center">
                <BreadCrumb title="DASHBOARD" subtitle="Welcome to dashboard" />
            </Box>
        </Box>
    )
}

export default Dashboard
