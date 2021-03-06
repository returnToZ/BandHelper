# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2016-07-12 10:57
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('testCon', '0006_account_teacher'),
    ]

    operations = [
        migrations.CreateModel(
            name='Course_Pick',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
            ],
        ),
        migrations.CreateModel(
            name='Student_Info',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('StuName', models.CharField(max_length=20)),
                ('Birthday', models.DateField()),
                ('ClassBelong', models.CharField(max_length=20)),
                ('Gender', models.CharField(max_length=2)),
            ],
        ),
        migrations.DeleteModel(
            name='Student',
        ),
        migrations.AlterField(
            model_name='account',
            name='username',
            field=models.CharField(max_length=20, unique=True),
        ),
        migrations.AlterField(
            model_name='classinfo',
            name='className',
            field=models.CharField(max_length=20, unique=True),
        ),
        migrations.AddField(
            model_name='student_info',
            name='username',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='testCon.Account', to_field='username'),
        ),
        migrations.AddField(
            model_name='course_pick',
            name='className',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='testCon.ClassInfo', to_field='className'),
        ),
        migrations.AddField(
            model_name='course_pick',
            name='username',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='testCon.Account', to_field='username'),
        ),
    ]
