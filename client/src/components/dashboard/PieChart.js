import React from 'react'
import { useTheme } from '@mui/material'
import { ResponsivePie } from '@nivo/pie'

import { SolvedCountByLevel } from '../../data/mockData'
import { tokens } from '../../config/theme'

const PieChart = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)

    return (
        <ResponsivePie
            data={SolvedCountByLevel}
            theme={{
                textColor: colors.grey['100'],
                tooltip: {
                    container: {
                        color: colors.grey['400'],
                    },
                },
            }}
            id="level"
            value="count"
            margin={{ top: 20, right: 60, left: 60 }}
            innerRadius={0.5}
            padAngle={0.7}
            cornerRadius={3}
            activeOuterRadiusOffset={8}
            borderWidth={0}
            sortByValue={true}
            defs={[
                {
                    id: 'unrated',
                    type: 'patternDots',
                    background: 'black',
                    color: 'black',
                },
                {
                    id: 'bronze',
                    type: 'patternDots',
                    background: 'rgb(173, 86, 0)',
                    color: 'rgb(173, 86, 0)',
                },
                {
                    id: 'silver',
                    background: 'rgb(67, 95, 122)',
                    color: 'rgb(67, 95, 122)',
                    type: 'patternDots',
                },
                {
                    id: 'gold',
                    background: 'rgb(236, 154, 0)',
                    color: 'rgb(236, 154, 0)',
                    type: 'patternDots',
                },
                {
                    id: 'platinum',
                    background: 'rgb(39, 226, 164)',
                    color: 'rgb(39, 226, 164)',
                    type: 'patternDots',
                },
                {
                    id: 'diamond',
                    background: 'rgb(0, 180, 252)',
                    color: 'rgb(0, 180, 252)',
                    type: 'patternDots',
                },
                {
                    id: 'ruby',
                    background: 'rgb(255, 0, 98)',
                    color: 'rgb(255, 0, 98)',
                    type: 'patternDots',
                },
            ]}
            fill={[
                {
                    match: {
                        id: 'unrated',
                    },
                    id: 'unrated',
                },
                {
                    match: {
                        id: 'bronze',
                    },
                    id: 'bronze',
                },
                {
                    match: {
                        id: 'silver',
                    },
                    id: 'silver',
                },
                {
                    match: {
                        id: 'gold',
                    },
                    id: 'gold',
                },
                {
                    match: {
                        id: 'platinum',
                    },
                    id: 'platinum',
                },
                {
                    match: {
                        id: 'diamond',
                    },
                    id: 'diamond',
                },
                {
                    match: {
                        id: 'ruby',
                    },
                    id: 'ruby',
                },
            ]}
            arcLinkLabelsSkipAngle={10}
            arcLinkLabelsTextColor="#333333"
            arcLinkLabelsThickness={2}
            arcLabelsSkipAngle={10}
            enableArcLabels={false}
            enableArcLinkLabels={false}
            arcLabelsTextColor={{
                from: 'color',
                modifiers: [['darker', '1.8']],
            }}
            borderColor={{
                from: 'color',
                modifiers: [['darker', 0.2]],
            }}
            tooltip={(e, s) => <span>{`${e.datum.id}: ${e.datum.value}`}</span>}
        />
    )
}

export default PieChart
