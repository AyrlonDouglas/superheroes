package com.example.superheroes

import SuperheroesTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroesRepository

@Composable
fun HeroItem(modifier: Modifier = Modifier, hero: Hero) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(hero.nameRes), style = MaterialTheme.typography.displaySmall
                )
                Text(
                    stringResource(hero.descriptionRes), style = MaterialTheme.typography.bodyLarge
                )
            }

            Image(
                painter = painterResource(hero.imageRes),
                contentDescription = stringResource(hero.descriptionRes),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(72.dp),
                contentScale = ContentScale.Crop
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun HeroItemPreview() {
    val hero = HeroesRepository.heroes[0]
    Box(modifier = Modifier.padding(24.dp)) {
        SuperheroesTheme {
            HeroItem(
                hero = hero,
                modifier = Modifier
                    .wrapContentSize()
                    .width(200.dp)
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun HeroesList(modifier: Modifier = Modifier) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    val heroes = HeroesRepository.heroes

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(heroes) { index, hero ->
                Row {
                    HeroItem(
                        hero = hero, modifier = Modifier.animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ), initialOffsetY = { it * (index + 1) })
                        )
                    )
                }
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun HeroesListPreview() {
    SuperheroesTheme(darkTheme = false) {
        HeroesList()
    }
}