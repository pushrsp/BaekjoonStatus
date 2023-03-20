import React from 'react'
import { Box } from '@mui/material'

import RadarChart from '../components/dashboard/RadarChart'
import PieChart from '../components/dashboard/PieChart'
import CalendarChart from '../components/dashboard/CalendarChart'

const Dashboard = () => {
    return (
        <Box m="20px" height="100%">
            <Box width="100%" height="50%" display="flex" justifyContent="center">
                <CalendarChart />
            </Box>
            <Box width="100%" height="50%" display="flex" justifyContent="space-between">
                <Box height="100%" width="30%" margin={0}>
                    <RadarChart />
                </Box>
                <Box height="100%" width="30%" margin={0}>
                    <PieChart />
                </Box>
                <Box height="100%" width="30%" margin={0}>
                    <PieChart />
                </Box>
            </Box>
            <Box width="100%" height="50%" display="flex" justifyContent="space-around">
                <Box height="100%" width="30%" margin={0}>
                    <RadarChart />
                </Box>
                <Box height="100%" width="30%" margin={0}>
                    <PieChart />
                </Box>
            </Box>
        </Box>
    )
}

export default Dashboard
