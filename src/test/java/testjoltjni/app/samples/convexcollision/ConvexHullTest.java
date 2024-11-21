/*
Copyright (c) 2024 Stephen Gold

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package testjoltjni.app.samples.convexcollision;
import com.github.stephengold.joltjni.*;
import com.github.stephengold.joltjni.enumerate.*;
import com.github.stephengold.joltjni.operator.Op;
import java.util.*;
import testjoltjni.app.samples.*;
import static com.github.stephengold.joltjni.Jolt.*;
import static testjoltjni.app.samples.DebugRendererSP.*;

/**
 * A line-for-line Java translation of the Jolt Physics convex-hull test.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/Samples/Tests/Constraints/ConvexHullTest.cpp
 */
public class ConvexHullTest extends Test{
DefaultRandomEngine mRandom=new DefaultRandomEngine( 12345 );
int mIteration=0;
List<List<Vec3>>mPoints;

public void Initialize()
{
	// First add a list of shapes that were problematic before
	mPoints = new ArrayList<>();
		mPoints.add(List.of(
			new Vec3(-1, 0, -1),
			new Vec3(1, 0, -1),
			new Vec3(-1, 0, 1),
			new Vec3(1, 0, 1)
		));
		mPoints.add(List.of(
			new Vec3(-1, 0, -1),
			new Vec3(1, 0, -1),
			new Vec3(-1, 0, 1),
			new Vec3(-0.5f, 0, -0.5f)
		));
		mPoints.add(List.of(
			new Vec3(-1, 0, -1),
			new Vec3(1, 0, -1),
			new Vec3(-1, 0, 1),
			new Vec3(1, 0, 1),
			new Vec3(0, 1, 0)
		));
		mPoints.add(List.of(
			new Vec3(1.25793016f, 0.157113776f, 1.22066617f),
			new Vec3(1.92657053f, 0.157114446f, 0.240761176f),
			new Vec3(1.40259242f, 0.157115221f, -0.834863901f),
			new Vec3(1.94086421f, 0.157113507f, -0.790734947f),
			new Vec3(2.20533752f, 0.157113209f, -0.281754375f),
			new Vec3(0.0426187329f, 0.157113969f, -1.40533638f),
			new Vec3(1.11055744f, 0.157113969f, -1.33626819f),
			new Vec3(0.180490851f, 0.157114655f, 1.16420007f),
			new Vec3(-1.34696794f, 0.157110974f, -0.978962243f),
			new Vec3(-0.981223822f, 0.157110706f, -1.44589376f),
			new Vec3(-1.8200444f, 0.157106474f, 1.05036092f),
			new Vec3(-0.376947045f, 0.15711388f, 1.13544536f),
			new Vec3(-1.37966835f, 0.157109678f, 1.08289516f),
			new Vec3(-1.04599845f, 0.157108605f, 1.54891157f),
			new Vec3(-0.597127378f, 0.157110557f, 1.57243586f),
			new Vec3(-2.09407234f, 0.157106325f, 0.560136259f),
			new Vec3(-1.91857386f, 0.157108605f, 0.0392456949f),
			new Vec3(-2.08503342f, 0.157106936f, -0.506603181f),
			new Vec3(-1.80278254f, 0.157107696f, -0.986931145f),
			new Vec3(0.434835076f, 0.157112151f, 1.62568307f),
			new Vec3(0.917346299f, 0.157111734f, 1.65097046f),
			new Vec3(1.77710009f, 0.157112047f, 1.2388792f),
			new Vec3(2.11432409f, 0.157112464f, 0.780689001f)
		));
		mPoints.add(List.of(
			new Vec3(1.32055235f, -0.0982032791f, 0.020047307f),
			new Vec3(-0.0175848603f, -0.104957283f, 0.020047307f),
			new Vec3(-0.0175848603f, 0.098285675f, 0.020047307f),
			new Vec3(1.32055235f, 0.098285675f, 0.020047307f),
			new Vec3(1.00427914f, -0.0982032791f, 0.868395209f),
			new Vec3(1.32055235f, -0.0982032791f, 2.63605499f),
			new Vec3(1.00427914f, -0.0982032791f, 1.95698023f),
			new Vec3(1.00427914f, -0.104957283f, 0.511006474f),
			new Vec3(0.00150847435f, -0.104957283f, 0.511006474f),
			new Vec3(0.271511227f, -0.179470509f, 0.868395209f),
			new Vec3(0.00150847435f, -0.179470509f, 0.868395209f),
			new Vec3(0.00150847435f, -0.179470509f, 0.511006474f),
			new Vec3(0.271511227f, -0.179470509f, 0.511006474f),
			new Vec3(1.00427914f, -0.145700991f, 1.95698023f),
			new Vec3(1.00427914f, -0.145700991f, 2.40789247f),
			new Vec3(0.271511227f, -0.179470509f, 2.40789247f),
			new Vec3(0.271511227f, -0.179470509f, 1.95698023f),
			new Vec3(0.00150847435f, -0.104957283f, 2.40789247f),
			new Vec3(1.00427914f, -0.104957283f, 2.40789247f),
			new Vec3(-0.0175848603f, -0.104957283f, 2.63605499f),
			new Vec3(1.32055235f, 0.098285675f, 2.63605499f),
			new Vec3(-0.0175848603f, 0.098285675f, 2.63605499f),
			new Vec3(-0.0175848603f, -0.0929760709f, 1.31891572f),
			new Vec3(-0.0175848603f, 0.0915316716f, 1.31891572f),
			new Vec3(1.00427914f, -0.145700991f, 0.868395209f),
			new Vec3(1.00427914f, -0.145700991f, 0.511006474f),
			new Vec3(0.00150847435f, -0.104957283f, 0.868395209f),
			new Vec3(0.00150847435f, -0.104957283f, 1.95698023f),
			new Vec3(0.00150847435f, -0.179470509f, 1.95698023f),
			new Vec3(0.00150847435f, -0.179470509f, 2.40789247f),
			new Vec3(-0.0175848603f, -0.100129686f, 0.959797204f),
			new Vec3(0.0878298879f, 0.139223307f, 1.04704332f),
			new Vec3(0.122709334f, -0.147821367f, 1.15395057f),
			new Vec3(0.122709334f, 0.139223307f, 1.15395057f),
			new Vec3(0.19671753f, -0.118080139f, 1.15425301f),
			new Vec3(0.0986568928f, -0.147821367f, 1.22612f),
			new Vec3(0.175069571f, -0.118080139f, 1.2711879f),
			new Vec3(-0.0175848603f, -0.147821367f, 0.959797204f),
			new Vec3(0.0767889619f, -0.118080139f, 0.947003484f),
			new Vec3(0.0878298879f, -0.147821367f, 1.04704332f),
			new Vec3(0.18563965f, -0.118080139f, 1.03236175f),
			new Vec3(-0.0175848603f, 0.098285675f, 0.959797204f),
			new Vec3(0.0986568928f, 0.139223307f, 1.22612f),
			new Vec3(0.0897113085f, -0.104957283f, 1.32667887f),
			new Vec3(-0.0175848603f, -0.147821367f, 1.31891572f),
			new Vec3(0.0897113085f, -0.118080139f, 1.32667887f),
			new Vec3(0.175069571f, -0.104957283f, 1.2711879f),
			new Vec3(0.18563965f, -0.104957283f, 1.03236175f),
			new Vec3(0.19671753f, -0.104957283f, 1.15425301f),
			new Vec3(0.0767889619f, -0.104957283f, 0.947003484f),
			new Vec3(1.00427914f, 0.098285675f, 0.868395209f),
			new Vec3(1.00427914f, 0.098285675f, 1.95698023f),
			new Vec3(1.00427914f, 0.098285675f, 0.511006474f),
			new Vec3(0.00150847435f, 0.098285675f, 0.511006474f),
			new Vec3(0.00150847435f, 0.17087248f, 0.511006474f),
			new Vec3(0.00150847435f, 0.17087248f, 0.868395209f),
			new Vec3(0.271511227f, 0.17087248f, 0.868395209f),
			new Vec3(0.271511227f, 0.17087248f, 0.511006474f),
			new Vec3(0.271511227f, 0.17087248f, 2.40789247f),
			new Vec3(1.00427914f, 0.137102962f, 2.40789247f),
			new Vec3(1.00427914f, 0.137102962f, 1.95698023f),
			new Vec3(0.271511227f, 0.17087248f, 1.95698023f),
			new Vec3(0.00150847435f, 0.098285675f, 2.40789247f),
			new Vec3(1.00427914f, 0.098285675f, 2.40789247f),
			new Vec3(1.00427914f, 0.137102962f, 0.868395209f),
			new Vec3(1.00427914f, 0.137102962f, 0.511006474f),
			new Vec3(0.00150847435f, 0.098285675f, 0.868395209f),
			new Vec3(0.00150847435f, 0.098285675f, 1.95698023f),
			new Vec3(0.00150847435f, 0.17087248f, 1.95698023f),
			new Vec3(0.00150847435f, 0.17087248f, 2.40789247f),
			new Vec3(0.19671753f, 0.109482117f, 1.15425301f),
			new Vec3(0.175069571f, 0.109482117f, 1.2711879f),
			new Vec3(-0.0175848603f, 0.139223307f, 0.959797204f),
			new Vec3(0.0767889619f, 0.109482117f, 0.947003484f),
			new Vec3(0.18563965f, 0.109482117f, 1.03236175f),
			new Vec3(0.0897113085f, 0.098285675f, 1.32667887f),
			new Vec3(-0.0175848603f, 0.139223307f, 1.31891572f),
			new Vec3(0.0897113085f, 0.109482117f, 1.32667887f),
			new Vec3(0.175069571f, 0.098285675f, 1.2711879f),
			new Vec3(0.19671753f, 0.098285675f, 1.15425301f),
			new Vec3(0.18563965f, 0.098285675f, 1.03236175f),
			new Vec3(0.0767889619f, 0.098285675f, 0.947003484f)
		));
		mPoints.add(List.of(
			new Vec3(0.0212580804f, 1.29376173f, 0.0102035152f),
			new Vec3(0.0225791596f, 1.05854928f, 0.0887729526f),
			new Vec3(0.0596007220f, 0.984267414f, 0.0408750288f),
			new Vec3(0.0722020790f, 0.980246127f, -0.0416274220f),
			new Vec3(-0.00376634207f, -0.718282819f, 0.00411359267f),
			new Vec3(-0.00188124576f, -0.718283117f, 0.00229378697f),
			new Vec3(-0.00162511703f, -0.718282461f, 0.00753012672f),
			new Vec3(-0.00118427153f, 1.36079276f, 0.00107491738f),
			new Vec3(-6.78644137e-05f, -0.718282998f, 0.00426622201f),
			new Vec3(0.00102991192f, 1.29927433f, 0.0230795704f),
			new Vec3(0.00699944887f, 1.05855191f, 0.0887731761f),
			new Vec3(-0.00603519706f, 1.04913890f, -0.102404378f),
			new Vec3(-0.0212373994f, 1.31092644f, 0.00530112581f),
			new Vec3(-0.0542707182f, 1.07623804f, 0.0403260253f),
			new Vec3(-0.0946691483f, 1.07357991f, -0.0185115524f),
			new Vec3(-0.0946691483f, 1.07357991f, -0.0185115524f)
		));
		mPoints.add(List.of(
			new Vec3(0.0283679180f, 0.0443800166f, -0.00569444988f),
			new Vec3(0.0327114500f, -0.0221119970f, 0.0232404359f),
			new Vec3(0.0374971032f, 0.0148781445f, -0.0245264377f),
			new Vec3(0.0439460576f, 0.0126368264f, 0.0197663195f),
			new Vec3(-0.0327170566f, 0.0423904508f, 0.0181609988f),
			new Vec3(-0.0306955911f, 0.0311534479f, -0.0281516202f),
			new Vec3(-0.0262422040f, 0.0248970203f, 0.0450032614f),
			new Vec3(-0.0262093470f, 0.00906597450f, 0.0481815264f),
			new Vec3(-0.0256845430f, -0.00607067533f, -0.0401362479f),
			new Vec3(-0.0179684199f, 0.0266145933f, -0.0394567028f),
			new Vec3(-0.00567848794f, -0.0313231349f, -0.0263656937f),
			new Vec3(-0.00444967486f, -0.0383231938f, 0.0206601117f),
			new Vec3(-0.00329093798f, 0.0464436933f, 0.0343827978f),
			new Vec3(-0.00225042878f, 0.0550651476f, -0.00304153794f),
			new Vec3(0.00310287252f, 0.00219658483f, 0.0542362332f),
			new Vec3(0.00435558241f, 0.00644031307f, -0.0455060303f),
			new Vec3(0.00495047215f, -0.0144955292f, 0.0482611060f),
			new Vec3(0.00510909408f, 0.0300753452f, -0.0415933356f),
			new Vec3(0.00619197031f, 0.0269140154f, 0.0500008501f),
			new Vec3(0.0190936550f, -0.0106478147f, 0.0453430638f),
			new Vec3(0.0202461667f, 0.00821140409f, 0.0500608832f),
			new Vec3(0.0199985132f, 0.0353404805f, 0.0413853638f),
			new Vec3(0.0267947838f, -0.0155944452f, -0.0300960485f),
			new Vec3(0.0274163429f, 0.0318853259f, -0.0288569275f),
			new Vec3(-0.0404368788f, -0.0213200711f, -0.00530833099f),
			new Vec3(-0.0383560173f, -0.0111571737f, 0.0346816145f),
			new Vec3(-0.0453024730f, 0.00178011740f, -0.0218658112f),
			new Vec3(-0.0482929349f, 0.0101582557f, 0.0191618335f)
		));
		mPoints.add(List.of(
			new Vec3(0.19555497f, 0.06892325f, 0.21078214f),
			new Vec3(0.20527978f, -0.01703966f, -0.09207391f),
			new Vec3(0.21142941f, 0.01785821f, -0.09836373f),
			new Vec3(0.21466828f, 0.05084385f, -0.03549951f),
			new Vec3(-0.20511348f, -0.07018351f, -0.31925454f),
			new Vec3(-0.19310803f, -0.13756239f, -0.33457401f),
			new Vec3(-0.20095457f, -0.09572067f, -0.11383702f),
			new Vec3(-0.18695570f, -0.14865115f, -0.19356145f),
			new Vec3(-0.18073241f, -0.08639215f, -0.35319963f),
			new Vec3(-0.18014188f, -0.15241129f, -0.34185338f),
			new Vec3(-0.18174356f, -0.15312561f, -0.19147469f),
			new Vec3(-0.19579467f, 0.01310298f, -0.00632396f),
			new Vec3(-0.16814114f, -0.05610058f, -0.34890732f),
			new Vec3(-0.16448530f, -0.16787034f, -0.29141789f),
			new Vec3(-0.17525161f, 0.01533679f, 0.08730947f),
			new Vec3(-0.17286175f, 0.08774700f, -0.01591185f),
			new Vec3(-0.17077128f, 0.01983560f, 0.10070839f),
			new Vec3(-0.14615997f, -0.16541340f, -0.37489247f),
			new Vec3(-0.14595763f, -0.16490393f, -0.37515628f),
			new Vec3(-0.16272801f, 0.07975677f, 0.08464866f),
			new Vec3(-0.13369306f, -0.06286648f, -0.37556374f),
			new Vec3(-0.14785704f, 0.14323678f, -0.01563696f),
			new Vec3(-0.12817731f, -0.04268694f, -0.36287897f),
			new Vec3(-0.14112462f, 0.13547241f, 0.05140329f),
			new Vec3(-0.12341158f, -0.17782864f, -0.36954373f),
			new Vec3(-0.12310848f, -0.18070405f, -0.20412853f),
			new Vec3(-0.09967888f, -0.18289816f, -0.38768309f),
			new Vec3(-0.09960851f, 0.14144828f, 0.12903015f),
			new Vec3(-0.08962545f, -0.17236463f, -0.39919903f),
			new Vec3(-0.09338194f, -0.00865331f, 0.23358464f),
			new Vec3(-0.09496998f, 0.17418922f, 0.03730623f),
			new Vec3(-0.09499961f, 0.16077143f, -0.03914160f),
			new Vec3(-0.08221246f, -0.07778487f, -0.39787262f),
			new Vec3(-0.07918695f, -0.14616625f, -0.40242865f),
			new Vec3(-0.08256439f, 0.01469633f, 0.24209134f),
			new Vec3(-0.07199146f, 0.16959090f, 0.11185526f),
			new Vec3(-0.05876892f, -0.18819671f, -0.40239989f),
			new Vec3(-0.05744339f, -0.18692162f, -0.40386000f),
			new Vec3(-0.04441069f, -0.04126521f, -0.37501192f),
			new Vec3(-0.04648328f, 0.18093951f, 0.03905040f),
			new Vec3(-0.03611449f, -0.14904837f, -0.40508240f),
			new Vec3(-0.03163360f, 0.17144355f, 0.13303288f),
			new Vec3(-0.02255749f, -0.01798030f, 0.33883106f),
			new Vec3(-0.01062212f, -0.11764656f, -0.39784804f),
			new Vec3(0.00002799f, -0.18946082f, -0.39155373f),
			new Vec3(0.00190875f, -0.16691279f, -0.40337407f),
			new Vec3(0.02337403f, -0.03170533f, 0.38295418f),
			new Vec3(0.02689898f, -0.03111388f, 0.38642361f),
			new Vec3(0.03513940f, -0.09795553f, -0.38733068f),
			new Vec3(0.04139633f, -0.18845227f, -0.32015734f),
			new Vec3(0.04843888f, 0.12765829f, -0.09677977f),
			new Vec3(0.04454701f, -0.14539991f, -0.38590988f),
			new Vec3(0.04690936f, -0.17584648f, -0.38177087f),
			new Vec3(0.05052238f, -0.18907529f, -0.35411724f),
			new Vec3(0.07129140f, -0.02806735f, 0.41684112f),
			new Vec3(0.07599759f, 0.02516599f, 0.43382310f),
			new Vec3(0.08328492f, -0.18135514f, -0.32588836f),
			new Vec3(0.08443428f, 0.07232403f, 0.37877142f),
			new Vec3(0.09074404f, -0.15272216f, -0.36002999f),
			new Vec3(0.09381036f, -0.04931259f, -0.32999005f),
			new Vec3(0.09348832f, -0.17767928f, -0.33666068f),
			new Vec3(0.09247280f, -0.01328942f, 0.44227284f),
			new Vec3(0.09364306f, 0.03557658f, 0.44191616f),
			new Vec3(0.09611026f, -0.01203391f, 0.44345939f),
			new Vec3(0.09662163f, 0.03456752f, 0.44326156f),
			new Vec3(0.10482377f, 0.12817247f, 0.27224415f),
			new Vec3(0.11271536f, 0.12685699f, 0.26856660f),
			new Vec3(0.10957191f, 0.03837919f, 0.43455946f),
			new Vec3(0.11146642f, -0.01284471f, 0.42120608f),
			new Vec3(0.11088928f, 0.00377234f, 0.44789928f),
			new Vec3(0.11571233f, -0.12474029f, -0.34762913f),
			new Vec3(0.12183426f, -0.16410264f, -0.30295142f),
			new Vec3(0.12211698f, 0.01099167f, 0.44373258f),
			new Vec3(0.12308656f, 0.01315179f, 0.44303578f),
			new Vec3(0.13090495f, -0.15086941f, -0.31031519f),
			new Vec3(0.14427974f, 0.09778974f, 0.30786031f),
			new Vec3(0.14200252f, 0.01419945f, 0.41783332f),
			new Vec3(0.14424091f, 0.06972501f, 0.37377491f),
			new Vec3(0.14422383f, 0.02227210f, 0.41717034f),
			new Vec3(0.15133176f, -0.03861540f, -0.27380293f),
			new Vec3(0.14738929f, 0.06972805f, 0.37101438f),
			new Vec3(0.15116664f, -0.13012324f, -0.26891800f),
			new Vec3(0.15432675f, -0.05065062f, -0.27696538f),
			new Vec3(0.17231981f, 0.09891064f, -0.04109610f),
			new Vec3(0.15486444f, 0.03080789f, 0.39333733f),
			new Vec3(0.16293872f, 0.09977609f, 0.23133035f),
			new Vec3(0.17278114f, 0.05925680f, -0.13166353f),
			new Vec3(0.17344120f, 0.06815492f, 0.29800513f),
			new Vec3(0.18346339f, 0.03002923f, -0.16944433f),
			new Vec3(0.18475264f, -0.03337195f, -0.21144425f),
			new Vec3(0.18153211f, 0.05077920f, 0.29410797f),
			new Vec3(0.18872119f, 0.08419117f, 0.18681980f),
			new Vec3(0.19402013f, 0.03129275f, -0.14645814f),
			new Vec3(0.20299899f, 0.06450803f, -0.05323168f),
			new Vec3(-0.20916573f, -0.14482390f, -0.28754678f),
			new Vec3(-0.21912349f, -0.12297497f, -0.25853595f),
			new Vec3(-0.21891747f, -0.11492035f, -0.30946639f),
			new Vec3(-0.22503024f, -0.09871494f, -0.27031892f),
			new Vec3(-0.22503024f, -0.09871494f, -0.27031892f),
			new Vec3(-0.22503024f, -0.09871494f, -0.27031892f)
		));
		mPoints.add(List.of(
			new Vec3(0.28483882f, 0.09470236f, 0.11433057f),
			new Vec3(0.30260321f, 0.07340867f, 0.00849266f),
			new Vec3(0.30380272f, 0.05582517f, -0.22405298f),
			new Vec3(0.30670973f, 0.02778204f, -0.22415190f),
			new Vec3(-0.29766368f, -0.06492511f, -0.19135096f),
			new Vec3(-0.28324991f, 0.02856347f, 0.16558051f),
			new Vec3(-0.27339774f, 0.11253071f, -0.13812468f),
			new Vec3(-0.26324614f, -0.03483995f, 0.34903234f),
			new Vec3(-0.27118766f, -0.15035510f, -0.06431498f),
			new Vec3(-0.26041472f, 0.10464326f, -0.20795805f),
			new Vec3(-0.22156618f, -0.00712212f, 0.40348106f),
			new Vec3(-0.20013636f, 0.13795423f, -0.23888915f),
			new Vec3(-0.19368620f, 0.04208890f, 0.42129427f),
			new Vec3(-0.18170905f, -0.10169907f, 0.38139578f),
			new Vec3(-0.18724660f, 0.18995818f, 0.08522552f),
			new Vec3(-0.17479378f, -0.05597380f, 0.41057986f),
			new Vec3(-0.15012621f, 0.08595391f, 0.43914794f),
			new Vec3(-0.11722116f, -0.10298516f, -0.30289822f),
			new Vec3(-0.11217459f, 0.00596011f, 0.44133874f),
			new Vec3(-0.11709289f, 0.23012112f, 0.12055066f),
			new Vec3(-0.10705470f, 0.15775623f, -0.33419770f),
			new Vec3(-0.08655276f, 0.09824081f, 0.43651989f),
			new Vec3(-0.08401379f, 0.08668444f, -0.41111666f),
			new Vec3(-0.08026488f, -0.24695427f, -0.01228247f),
			new Vec3(-0.06294082f, 0.12666735f, -0.39178270f),
			new Vec3(-0.05308891f, -0.07724215f, -0.37346649f),
			new Vec3(-0.04869145f, -0.23846265f, -0.11154356f),
			new Vec3(-0.04377052f, 0.06346821f, 0.44263243f),
			new Vec3(-0.03821557f, 0.05776290f, -0.43330976f),
			new Vec3(-0.01401243f, -0.07849873f, 0.37016886f),
			new Vec3(-0.01267736f, -0.24327334f, -0.09846258f),
			new Vec3(-0.00871999f, -0.24532425f, -0.01158716f),
			new Vec3(0.00610917f, 0.20575316f, -0.32363408f),
			new Vec3(0.01893912f, -0.02637211f, -0.44099009f),
			new Vec3(0.03742292f, 0.25572568f, 0.11976100f),
			new Vec3(0.04572892f, -0.02452080f, 0.37599292f),
			new Vec3(0.04809525f, 0.11413645f, 0.38247618f),
			new Vec3(0.04934106f, -0.01875172f, -0.43612641f),
			new Vec3(0.07854398f, 0.13351599f, 0.34539741f),
			new Vec3(0.11064179f, 0.03347895f, 0.33272063f),
			new Vec3(0.11110801f, 0.04016598f, -0.42360800f),
			new Vec3(0.12390327f, -0.20230874f, -0.01599736f),
			new Vec3(0.13082972f, -0.19843940f, -0.08606190f),
			new Vec3(0.12559986f, -0.02563187f, -0.38013845f),
			new Vec3(0.12924608f, 0.16206453f, -0.34893369f),
			new Vec3(0.15646456f, 0.21451330f, 0.16623015f),
			new Vec3(0.17851203f, -0.14074428f, 0.08427754f),
			new Vec3(0.19401437f, -0.15288332f, -0.03272480f),
			new Vec3(0.20102191f, 0.08705597f, -0.37915167f),
			new Vec3(0.20596674f, 0.06604006f, -0.38868805f),
			new Vec3(0.26085311f, 0.08702713f, -0.32507085f),
			new Vec3(0.27331018f, 0.15497627f, 0.11259682f),
			new Vec3(0.27269470f, 0.03719006f, -0.31962081f),
			new Vec3(0.27288356f, 0.06217747f, -0.33064606f),
			new Vec3(-0.29314118f, -0.18079891f, 0.24351751f),
			new Vec3(-0.30831277f, -0.06952596f, 0.07340523f),
			new Vec3(-0.30126276f, -0.18365636f, 0.22815129f),
			new Vec3(-0.30392047f, -0.17969127f, 0.22713920f),
			new Vec3(-0.30392047f, -0.17969127f, 0.22713920f),
			new Vec3(-0.30392047f, -0.17969127f, 0.22713920f)
		));
		mPoints.add(List.of(
			// A really small hull
			new Vec3(-0.00707678869f, 0.00559568405f, -0.0239779726f),
			new Vec3(0.0136205591f, 0.00541752577f, -0.0225500446f),
			new Vec3(0.0135576781f, 0.00559568405f, -0.0224227905f),
			new Vec3(-0.0108219199f, 0.00559568405f, -0.0223935191f),
			new Vec3(0.0137226451f, 0.00559568405f, -0.0220940933f),
			new Vec3(0.00301175844f, -0.0232942104f, -0.0214947499f),
			new Vec3(0.017349612f, 0.00559568405f, 0.0241708681f),
			new Vec3(0.00390899926f, -0.0368074179f, 0.0541367307f),
			new Vec3(-0.0164459459f, 0.00559568405f, 0.0607497096f),
			new Vec3(-0.0169881769f, 0.00559568405f, 0.0608173609f),
			new Vec3(-0.0168782212f, 0.0052883029f, 0.0613293499f),
			new Vec3(-0.00663783913f, 0.00559568405f, -0.024154868f),
			new Vec3(-0.00507298298f, 0.00559568405f, -0.0242112875f),
			new Vec3(-0.00565947127f, 0.00477081537f, -0.0243848339f),
			new Vec3(0.0118075963f, 0.00124305487f, -0.0258472487f),
			new Vec3(0.00860248506f, -0.00697988272f, -0.0276725553f)
		));
		mPoints.add(List.of(
			// Nearly co-planar hull (but not enough to go through the 2d hull builder)
			new Vec3(0.129325435f, -0.213319957f, 0.00901593268f),
			new Vec3(0.129251331f, -0.213436425f, 0.00932094082f),
			new Vec3(0.160741553f, -0.171540618f, 0.0494558439f),
			new Vec3(0.160671368f, -0.17165187f, 0.049765937f),
			new Vec3(0.14228563f, 0.432965666f, 0.282429159f),
			new Vec3(0.142746598f, 0.433226734f, 0.283286631f),
			new Vec3(0.296031296f, 0.226935148f, 0.312804461f),
			new Vec3(0.296214104f, 0.227568939f, 0.313606918f),
			new Vec3(-0.00354258716f, -0.180767179f, -0.0762089267f),
			new Vec3(-0.00372517109f, -0.1805875f, -0.0766792595f),
			new Vec3(-0.0157070309f, -0.176182508f, -0.0833940506f),
			new Vec3(-0.0161666721f, -0.175898403f, -0.0840280354f),
			new Vec3(-0.342764735f, 0.0259497911f, -0.244388372f),
			new Vec3(-0.342298329f, 0.0256615728f, -0.24456653f),
			new Vec3(-0.366584063f, 0.0554589033f, -0.250078142f),
			new Vec3(-0.366478682f, 0.0556178838f, -0.250342518f)
		));
		mPoints.add(List.of(
			// A hull with a very acute angle that won't properly build when using distance to plane only
			new Vec3(-0.0451235026f, -0.103826642f, -0.0346511155f),
			new Vec3(-0.0194563419f, -0.123563275f, -0.032212317f),
			new Vec3(0.0323024541f, -0.0468643308f, -0.0307639092f),
			new Vec3(0.0412166864f, -0.0884782523f, -0.0288816988f),
			new Vec3(-0.0564572513f, 0.0207469314f, 0.0169318169f),
			new Vec3(0.00537410378f, 0.105688639f, 0.0355164111f),
			new Vec3(0.0209896415f, 0.117749952f, 0.0365252197f),
			new Vec3(0.0211542398f, 0.118546993f, 0.0375355929f)
		)
	);

	// Add a cube formed out of a regular grid of vertices, this shows how the algorithm deals
	// with many coplanar points
	{
		List<Vec3> p=new ArrayList<>(1000);
		for (int x = 0; x < 10; ++x)
			for (int y = 0; y < 10; ++y)
				for (int z = 0; z < 10; ++z)
					p.add(Op.multiply(Vec3.sReplicate(-0.5f) , Op.multiply(0.1f ,new Vec3((float)(x), (float)(y), (float)(z)))));
		mPoints.add(p);
	}

	// Add disc of many points
	{
		List<Vec3> p=new ArrayList<>();
		Mat44 rot = Mat44.sRotationZ(0.25f * JPH_PI);
		for (float r = 0.0f; r < 2.0f; r += 0.1f)
			for (float phi = 0.0f; phi <= 2.0f * JPH_PI; phi += 2.0f * JPH_PI / 20.0f)
				p.add(Op.multiply(rot ,new Vec3(r * cos(phi), r * sin(phi), 0)));
		mPoints.add(p);
	}

	// Add wedge shaped disc that is just above the hull tolerance on its widest side and zero on the other side
	{
		List<Vec3> p=new ArrayList<>();
		for (float phi = 0.0f; phi <= 2.0f * JPH_PI; phi += 2.0f * JPH_PI / 40.0f)
		{
			Vec3 pos=new Vec3(2.0f * cos(phi), 0, 2.0f * sin(phi));
			p.add(pos);
			p.add(Op.add(pos ,new Vec3(0, 2.0e-3f * (2.0f + pos.getX()) / 4.0f, 0)));
		}
		mPoints.add(p);
	}

	// Add a sphere of many points
	{
		List<Vec3> p=new ArrayList<>();
		for (float theta = 0.0f; theta <= JPH_PI; theta += JPH_PI / 20.0f)
			for (float phi = 0.0f; phi <= 2.0f * JPH_PI; phi += 2.0f * JPH_PI / 20.0f)
				p.add(Vec3.sUnitSpherical(theta, phi));
		mPoints.add(p);
	}

	// Open the external file with hulls
	// A stream containing predefined convex hulls
	StreamInWrapper points_stream=StreamInWrapper.open("Assets/convex_hulls.bin", StreamInWrapper.binary());
	if (points_stream!=null)
	{
		for (;;)
		{
			// Read the length of the next point cloud
			int len = 0;
			len=points_stream.readInt(  );
			if (points_stream.isEof())
				break;

			// Read the points
			if (len > 0)
			{
				List<Vec3> p=new ArrayList<>(len);
				for (int i = 0; i < len; ++i)
				{
					Float3 v=new Float3();
					points_stream.readFloat3( v );
					p.add(new Vec3(v));
				}
				mPoints.add(p);
			}
		}
	}
}

public void PrePhysicsUpdate( PreUpdateParams inParams)
{
	final float display_scale = 10.0f;

	float tolerance = 1.0e-3f;

	List<Vec3> points;
	if (mIteration < mPoints.size())
	{
		// Take one of the predefined shapes
		points = mPoints.get(mIteration);
	}
	else
	{
		UniformRealDistribution zero_one=new UniformRealDistribution(0.0f, 1.0f);
		UniformRealDistribution zero_two=new UniformRealDistribution(0.0f, 2.0f);

		// Define vertex scale
		UniformRealDistribution scale_start=new UniformRealDistribution(0.1f, 0.5f);
		UniformRealDistribution scale_range=new UniformRealDistribution(0.1f, 2.0f);
		float start = scale_start.nextFloat(mRandom);
		UniformRealDistribution vertex_scale=new UniformRealDistribution(start, start + scale_range.nextFloat(mRandom));

		// Define shape scale to make shape less sphere like
		UniformRealDistribution shape_scale=new UniformRealDistribution(0.1f, 1.0f);
		Vec3 scale=new Vec3(shape_scale.nextFloat(mRandom), shape_scale.nextFloat(mRandom), shape_scale.nextFloat(mRandom));

		// Add some random points
                points=new ArrayList<>(400);
		for (int i = 0; i < 100; ++i)
		{
			// Add random point
			Vec3 p1 = Op.multiply(vertex_scale.nextFloat(mRandom) , Op.multiply(Vec3.sRandom(mRandom) , scale));
			points.add(p1);

			// Point close to p1
			Vec3 p2 = Op.add(p1 , Op.multiply(tolerance * zero_two.nextFloat(mRandom) , Vec3.sRandom(mRandom)));
			points.add(p2);

			// Point on a line to another point
			float fraction = zero_one.nextFloat(mRandom);
			Vec3 p3 = Op.add(Op.multiply(fraction , p1) , Op.multiply((1.0f - fraction) , points.get(mRandom.nextInt() % points.size())));
			points.add(p3);

			// Point close to p3
			Vec3 p4 = Op.add(p3 , Op.multiply(tolerance * zero_two.nextFloat(mRandom) , Vec3.sRandom(mRandom)));
			points.add(p4);
		}
	}
	mIteration++;

	ConvexHullBuilder builder=new ConvexHullBuilder(points);

	// Build the hull
	String  []error = new String[1];
	EResult result = builder.initialize(INT_MAX, tolerance, error);
	if (result != EResult.Success && result != EResult.MaxVerticesReached)
	{
		Trace("Iteration %d: Failed to initialize from positions: %s", mIteration - 1, error[0]);
		assert(false);
		return;
	}

	// Determine center of mass
	Vec3 com=new Vec3();
	float[] vol=new float[1];
	builder.getCenterOfMassAndVolume(com, vol);

	// Test if all points are inside the hull with the given tolerance
	float[] max_error=new float[1], coplanar_distance=new float[1];
	int[] max_error_point=new int[1];
	ChbFace[] max_error_face=new ChbFace[1];
	builder.determineMaxError(max_error_face, max_error, max_error_point, coplanar_distance);

	// Check if error is bigger than 4 * the tolerance
	if (max_error[0] > 4.0f * Math.max(tolerance, coplanar_distance[0]))
	{
		Trace("Iteration %d: max_error=%g", mIteration - 1, (double)max_error[0]);

		// Draw point that had the max error
		Vec3 point = Op.multiply(display_scale , Op.subtract(points.get(max_error_point[0]) , com));
		DrawMarkerSP(mDebugRenderer, point, Color.sRed, 1.0f);
		DrawText3DSP(mDebugRenderer, point, String.format("%d: %g", max_error_point[0], (double)max_error[0]), Color.sRed);

		// Length of normal (2x area) for max error face
		Vec3 centroid = Op.multiply(display_scale , Op.subtract(max_error_face[0].getCentroid() , com));
		Vec3 centroid_plus_normal = Op.add(centroid , max_error_face[0].getNormal().normalized());
		DrawArrowSP(mDebugRenderer, centroid, centroid_plus_normal, Color.sGreen, 0.1f);
		DrawText3DSP(mDebugRenderer, centroid_plus_normal, Float.toString(max_error_face[0].getNormal().length()), Color.sGreen);

		// Draw face that had the max error
		 ChbEdge e = max_error_face[0].getFirstEdge();
		Vec3 prev = Op.multiply(display_scale , Op.subtract(points.get(e.getStartIdx()) , com));
		do
		{
			ChbEdge  next = e.getNextEdge();
			Vec3 cur = Op.multiply(display_scale , Op.subtract(points.get(next.getStartIdx()) , com));
			DrawArrowSP(mDebugRenderer, prev, cur, Color.sYellow, 0.01f);
			DrawText3DSP(mDebugRenderer, prev, Integer.toString(e.getStartIdx()), Color.sYellow);
			e = next;
			prev = cur;
		} while (e != max_error_face[0].getFirstEdge());

		assert(false);
	}

	// Draw input points around center of mass
	for (Vec3 v : points)
		DrawMarkerSP(mDebugRenderer, Op.multiply(display_scale , Op.subtract(v , com)), Color.sWhite, 0.01f);

	// Draw the hull around center of mass
	int color_idx = 0;
	for (ChbFace f : builder.getFaces())
	{
		Color color = Color.sGetDistinctColor(color_idx++);

		// First point
		 ChbEdge e = f.getFirstEdge();
		Vec3 p1 = Op.multiply(display_scale , Op.subtract(points.get(e.getStartIdx()) , com));

		// Second point
		e = e.getNextEdge();
		Vec3 p2 = Op.multiply(display_scale , Op.subtract(points.get(e.getStartIdx()) , com));

		// First line
		DrawLineSP(mDebugRenderer, p1, p2, Color.sGrey);

		do
		{
			// Third point
			e = e.getNextEdge();
			Vec3 p3 = Op.multiply(display_scale , Op.subtract(points.get(e.getStartIdx()) , com));

			DrawTriangleSP(mDebugRenderer, p1, p2, p3, color);

			DrawLineSP(mDebugRenderer, p2, p3, Color.sGrey);

			p2 = p3;
		}
		while (e.va() != f.getFirstEdge().va());
	}
}
}
