import React from 'react'
import { useTheme } from '@mui/material'
import { ResponsiveRadar } from '@nivo/radar'

import { SolvedCountByTag } from '../../data/mockData'
import { tokens } from '../../config/theme'

const RadarChart = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)

    return (
        <ResponsiveRadar
            data={SolvedCountByTag}
            theme={{
                textColor: colors.grey['100'],
                tooltip: {
                    container: {
                        color: colors.grey['400'],
                    },
                },
            }}
            sliceTooltip={(e) => <div>{e.data[0].formattedValue}</div>}
            keys={['count']}
            indexBy="tag"
            margin={{ top: 20, right: 60, left: 60 }}
            borderColor={{ from: 'background' }}
            gridLabelOffset={36}
            gridShape="linear"
            enableDots={false}
            colors={colors.greenAccent['600']}
            fillOpacity={1}
            blendMode={theme.palette.mode === 'dark' ? 'screen' : 'multiply'}
            motionConfig="wobbly"
        />
    )
}

export default RadarChart
